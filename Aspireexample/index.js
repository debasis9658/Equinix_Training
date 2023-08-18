
const db = require('./db_implementation/ddl');

async function main() {
    
    const db1 = new db('myDatabase');

    const dynamicSchema = {
        "name": { "regex": "[a-z]+", "type": "string" },
        "age": { "regex": "^[1-9]\\d+$", "type": "number" },
        "isManager": {"type": "boolean"}
    };

    const validData1 = {
        "name": "john",
        "age": 30,
        "isManager": true
    };
    const validData2 = {
        "name": "Debasish",
        "age": 30,
        "isManager": true
    };
    const validData3 = {
        "name": "Leets",
        "age": 22,
        "isManager": false
    };

    const invalidData1 = {
        "name": "alice",
        "age": -10,
        "isManager": true
    };
    const invalidData2 = {
        "name": "alice",
        "age": "778",
        "isManager": true
    };
    const invalidData3 = {
        "name": "alice",
        "age": 10
    };




    try{
        await db1.createJSONFile('demo', dynamicSchema);
        const id1 = await db1.addToJSONFile('demo', validData1);
        const id2 = await db1.addToJSONFile('demo', validData2);
        const id3 = await db1.addToJSONFile('demo', validData3);
        await db1.readAndPrintJSONFile('demo');
        const upadatedData = {
            "age": 32,
            "isManager": false
        }
        await db1.updateJSONElement('demo', id2, upadatedData);
        await db1.readAndPrintJSONFile('demo');
        await db1.deleteJSONElement('demo', id1);
        await db1.readAndPrintJSONFile('demo');
    }
    catch(err){
        console.error("Error:", err);
    }

}

main();

// use this to test our db implementation..  