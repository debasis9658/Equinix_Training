const fs = require('fs');
const path = require('path');

class Database {
    constructor(dbName) {
        this.folderName = dbName;
        this.folderPath = '';
    
        if (this.isValidFolderName(this.folderName)) {
          const parentDir = path.join(__dirname, '..');
          this.folderPath = path.join(parentDir, this.folderName);
    
          fs.mkdir(this.folderPath, { recursive: true }, (err) => {
            if (err) {
              console.error('Error creating folder:', err);
            } else {
              console.log(`Folder "${this.folderName}" created successfully in the parent directory`);
            }
          });
        } 
        else {
          console.error('Invalid folder name');
        }
    }
    
    isValidFolderName(folderName) {
        let folderNameRegex = /^(?!\d+$)[^\s]+$/;
        return folderNameRegex.test(folderName);
    }

    async createEmptyJSONFile(fileName, schema) {
        const filePath = path.join(this.folderPath, fileName + '.json');
        const emptyData = JSON.stringify(schema);

        fs.writeFile(filePath, emptyData, (err) => {
            if (err) {
                console.error('Error creating JSON file:', err);
            } else {
                console.log(`JSON file "${fileName}.json" created successfully in the folder`);
            }
        });
    }
    
    async addToJSONFile(fileName, data) {
        const filePath = path.join(this.folderPath, fileName + '.json');
    
        try {
            const fileData = await fs.promises.readFile(filePath, 'utf8');
            const jsonData = JSON.parse(fileData);
    
            jsonData.push(data);
    
            await fs.promises.writeFile(filePath, JSON.stringify(jsonData, null, 2));
    
            console.log('Data added to JSON file successfully');
        } catch (error) {
            console.error('Error:', error);
        }
    }
    
    async readAndPrintJSONFile(fileName) {
        const filePath = path.join(this.folderPath, fileName + '.json');
        
        try {
            const fileData = await new Promise((resolve, reject) => {
                fs.readFile(filePath, 'utf8', (err, data) => {
                    if (err) reject(err);
                    else resolve(data);
                });
            });
    
            const jsonData = JSON.parse(fileData);
            console.log('JSON data from file ',fileName, 'are :', jsonData);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    async updateJSONElement(fileName, elementKey, newData) {
        const filePath = path.join(this.folderPath, fileName + '.json');
    
        try {
            const fileData = await fs.promises.readFile(filePath, 'utf8');
            const jsonData = JSON.parse(fileData);
    
            const elementIndex = jsonData.findIndex(element => element.hasOwnProperty(elementKey));
    
            if (elementIndex !== -1) {
                jsonData[elementIndex] = { ...jsonData[elementIndex], ...newData };
    
                await fs.promises.writeFile(filePath, JSON.stringify(jsonData, null, 2));
    
                console.log('JSON element updated successfully');
            } else {
                console.error('Element key not found in JSON array');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }
    
    async deleteJSONElement(fileName, elementKey, keyValue) {
        const filePath = path.join(this.folderPath, fileName + '.json');
    
        try {
            const fileData = await fs.promises.readFile(filePath, 'utf8');
            const jsonData = JSON.parse(fileData);
    
            const elementIndex = jsonData.findIndex(element => element.hasOwnProperty(elementKey) && element[elementKey] === keyValue);
    
            if (elementIndex !== -1) {
                jsonData.splice(elementIndex, 1);
    
                await fs.promises.writeFile(filePath, JSON.stringify(jsonData, null, 2));
    
                console.log('JSON element deleted successfully');
            } else {
                console.error('Element key not found in JSON array');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }
        
}

module.exports = Database;









