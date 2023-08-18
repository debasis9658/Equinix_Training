
const db = require('./db_implementation/ddl');

async function main() {
    const db1 = new db('myDatabase');

    await db1.createEmptyJSONFile('data', []);
    await db1.addToJSONFile('data', { id: 1, name: 'John Doe' });
    await db1.addToJSONFile('data', { id: 2, name: 'Leets' });
    await db1.readAndPrintJSONFile('data');
    await db1.updateJSONElement('data', 'id', { id: 1, name: 'Debasish' });
    await db1.readAndPrintJSONFile('data');
    await db1.deleteJSONElement('data', 'id', 2);
    await db1.readAndPrintJSONFile('data');

    await db1.createEmptyJSONFile('data2', []);
    await db1.addToJSONFile('data2', { id: 1, name: 'Devs', age: 22, email: 'debasisjkbk@gmail.com'});
    await db1.addToJSONFile('data2', { id: 3, name: 'Jyo', age: 22, email: 'jyo@gmail.com'});
    await db1.addToJSONFile('data2', { id: 1, name: 'Vikash', age: 23, email: 'vik@gmail.com'});
    await db1.addToJSONFile('data2', { id: 1, name: 'Sunil', age: 25, email: 'sunil@gmail.com'});
    await db1.readAndPrintJSONFile('data2');
    await db1.updateJSONElement('data2', 'name', { id: 4, name: 'Devs', age: 29, email: 'kkk@gmail.com' });
    await db1.readAndPrintJSONFile('data2');

}

main();

// use this to test our db implementation..  