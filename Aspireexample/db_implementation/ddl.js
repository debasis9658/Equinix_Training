const fs = require('fs');
const path = require('path');
const { v4: uuidv4 } = require('uuid');

class Database {
    /** This constructor method for our Database class
     * 
     * @param {*} dbName is a string which takes Database name is the argument
     */ 
    constructor(dbName) {
        this.databaseName = dbName;
        this.databasePath = '';
        this.initialize();
    }
    /** This is an internal method to acheive async nature of constructor
     * Throws error if there are any problem with database creation
     */
    async initialize() {
        if (this.isDuplicatePresent(this.databaseName)) {
            console.error('Error creating folder: Cannot create a Database with the same name as an existing one');
            throw new Error('Cannot create a Database with the same name as an existing one');
        }

        if (this.isValidDBName(this.databaseName)) {
            const parentDir = path.join(__dirname, '..');
            this.databasePath = path.join(parentDir, this.databaseName);

            try {
                await new Promise((resolve, reject) => {
                    fs.mkdir(this.databasePath, { recursive: true }, (err) => {
                        if (err) {
                            reject(err);
                        } else {
                            console.log(`Folder "${this.databaseName}" created successfully in the parent directory`);
                            resolve();
                        }
                    });
                });
            } catch (err) {
                console.error('Error creating folder:', err);
            }
        } else {
            throw new Error('invalid folder name');
        }
    }
    /** This is an internal method to check if the directory was previously present or not
     * @param {*} databaseName as string
     * @returns a bool value if duplicate is present 
     */
    isDuplicatePresent(databaseName){
        const parentDir = path.join(__dirname, '..');
        const databasePath = path.join(parentDir, databaseName);
        
        try{
            const stats = fs.statSync(databasePath);
            if(stats.isDirectory()){
                return true;
            }
        }
        catch(err){
            if(err.code === 'ENOENT'){
                return false;
            }
        }
        return false;
    }
    /** This is an internal method to check if the databse name is acceptable or not
     * @param {*} dBName as string 
     * @returns a bool value representing DB creation for table storage
     */
    isValidDBName(dBName) {
        let databaseNameRegex = /^(?!\d+$)[^\s]+$/;
        return databaseNameRegex.test(dBName);
    }
    /** This method creates a table with the help of schema
     * @param {*} tableName as string which is going to be the name of the table
     * @param {*} schema as JSON schema format which is to be followed by all the entries
     */
    async createJSONFile(tableName, schema) {
        if (this.databaseName == '') {
            throw new Error('No Db detected');
        }
        const tablePath = path.join(this.databasePath, tableName + '.json');
    
        try {
            await new Promise((resolve, reject) => {
                fs.mkdir(this.databasePath, { recursive: true }, (err) => {
                    if (err) {
                        reject(err);
                    } else {
                        resolve();
                    }
                });
            });
    
            const jsonData = {
                "table_name": tableName,
                "schema": schema,
                "data": []
            };
            const schemaData = JSON.stringify(jsonData, null, 2);
    
            fs.writeFile(tablePath, schemaData, (err) => {
                if (err) {
                    console.error('Error creating JSON file:', err);
                } else {
                    console.log(`JSON file "${tableName}.json" created successfully in the folder`);
                }
            });
        } catch (err) {
            console.error('Error creating folder:', err);
        }
    }
    /** This is an internal method in order to check if a new entry or updated 
     * entries are following the schema format.
     * @param {*} schema as the JSON schema to be followed by the data
     * @param {*} data as string which is to be checked
     * @returns a bool value represnting verification of data with schema
     */
    isValidSchema(schema, data){
        const schemaFields = Object.keys(schema);
        const dataFields = Object.keys(data);

        if (schemaFields.length !== dataFields.length) {
            return false;
        }

        for(const field in schema){
            const fieldInfo = schema[field];
            const fieldVal = data[field];

            if (fieldInfo.regex && !new RegExp(fieldInfo.regex).test(fieldVal)) {
                return false;
            }
            if (fieldInfo.type && typeof fieldVal !== fieldInfo.type) {
                return false;
            }
        }

        return true;
    }
    /** This is createTable operation analogous method, which checks the incomming data
     * and creates an entry for this element with an unique id for identification.
     * @param {*} tableName as String which is Table name
     * @param {*} inCommingData JSON format new data to be added
     * @returns the unique id which will be used for identication and other processes
     */
    async addToJSONFile(tableName, inCommingData) {
        const tablePath = path.join(this.databasePath, tableName + '.json');
        
        try {
            const tableData = await fs.promises.readFile(tablePath, 'utf8');
            const jsonData = JSON.parse(tableData);
            let prevData = jsonData["data"];
            const schema = jsonData["schema"];

            if (this.isValidSchema(schema, inCommingData)) {
                const randomNum = uuidv4();
                const newData = { id: randomNum, ...inCommingData }; // Generate a random UUID
                prevData.push(newData);
                
                await fs.promises.writeFile(tablePath, JSON.stringify(jsonData, null, 2));
                console.log('Data added to JSON file successfully');
                return randomNum

            } else {
                throw new Error('Incoming data format is wrong');
            }
        } catch (error) {
            console.error('Error:', error);
            return null;
        }
    }
    /** This method reads the content of the table
     * @param {*} tableName as String
     */
    async readAndPrintJSONFile(tableName) {
        const tablePath = path.join(this.databasePath, tableName + '.json');
        
        try {
            const tableData = await new Promise((resolve, reject) => {
                fs.readFile(tablePath, 'utf8', (err, data) => {
                    if (err) reject(err);
                    else resolve(data);
                });
            });
    
            const jsonData = JSON.parse(tableData);
            console.log('JSON data from file ',tableName, 'are :', jsonData);
        } catch (error) {
            console.error('Error:', error);
        }
    }
    /** This method updates an entry by identifying the entry with table name and unique id and 
     * data is replaced according to the new input with one or more changes in element and all validations are checked here.
     * @param {*} tableName as String
     * @param {*} elementId as an unique id for identification
     * @param {*} newData is a new element having one or more fields which should ideally follow the schema.
     */
    async updateJSONElement(tableName, elementId, newData) {
        const tablePath = path.join(this.databasePath, tableName + '.json');

        try {
            const tableData = await fs.promises.readFile(tablePath, 'utf8');
            const jsonData = JSON.parse(tableData);
            const prevData = jsonData["data"];
            const schema = jsonData["schema"];

            const elementIndex = prevData.findIndex(element => element.id === elementId);

            if (elementIndex !== -1) {

                const updatedElement = { ...prevData[elementIndex], ...newData };
                const { id, ...updatedDataWithoutId } = updatedElement;

                if (this.isValidSchema(schema, updatedDataWithoutId)) {

                    prevData[elementIndex] = updatedElement;
                    await fs.promises.writeFile(tablePath, JSON.stringify(jsonData, null, 2));
                    console.log('JSON element updated successfully');
                    
                } else {
                    throw new Error('Updated data format is wrong according to schema');
                }
            } else {
                console.error('Element with specified ID not found');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }
    /** This method is to delete an entry from the table
     * @param {*} tableName as String
     * @param {*} elementId which is an unique id to identify a particular entry 
     */
    async deleteJSONElement(tableName, elementId) {
        const tablePath = path.join(this.databasePath, tableName + '.json');

        try {
            const tableData = await fs.promises.readFile(tablePath, 'utf8');
            const jsonData = JSON.parse(tableData);
            const prevData = jsonData["data"];

            const elementIndex = prevData.findIndex(element => element.id === elementId);

            if (elementIndex !== -1) {
                prevData.splice(elementIndex, 1);

                await fs.promises.writeFile(tablePath, JSON.stringify(jsonData, null, 2));
                console.log('JSON element deleted successfully');
            } else {
                console.error('Element with specified ID not found');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

}

module.exports = Database;









