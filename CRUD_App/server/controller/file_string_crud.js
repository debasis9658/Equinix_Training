const fs = require('fs');
const { checkServerIdentity } = require('tls');

function insertIntoSortedFile(word) {
    try {
        const data = fs.readFileSync('newFile.txt', 'utf8');
        const lines = data.trim().split('\n');

        // Find the appropriate index to insert the incoming string
        let insertIndex = lines.findIndex(line => line > word);
        if (insertIndex === -1) {
            insertIndex = lines.length; // If it's the largest string, insert at the end
        }

        // Insert the incoming string at the appropriate position
        lines.splice(insertIndex, 0, word);

        const updatedContent = lines.join('\n');

        fs.writeFileSync('newFile.txt', updatedContent, 'utf8');
        return 'String inserted and file updated successfully';
    } catch (err) {
        throw err;
    }
}

function readFileContents(req, res) {
    try {
        const content = fs.readFileSync('newFile.txt', 'utf8');
        const lines = content.split('\n');
        const jsonData = { lines: [] };

        lines.forEach(line => {
            if (line.trim() !== '') {
                jsonData.lines.push(line.trim());
            }
        });

        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify(jsonData, null, 2));
    } catch (err) {
        console.log('Error reading file:', err.message);
        res.writeHead(500, { 'Content-Type': 'text/plain' });
        res.end('An error occurred while reading the file.');
    }
}

function checkValidation(str){
    const regex = /^[^\s]*$/;
    if(regex.test(str)){
        return true;
    }
    return false;
}

module.exports.create_file = function(req, res) {
    const content = 'This is the content to write into the new file.';
    fs.writeFile('newFile.txt', content, 'utf8', (err) => {
    if (err) {
        console.error(err);
        res.status(500).send('Error creating file.');
        return;
    }
    console.log('New file has been created.');
    });
    res.send('Text has been added to the file.');
}

module.exports.addString = function(req, res) {
    const str = req.params.stringToBeAdded;
    console.log(str);
    const originalContent = fs.readFileSync('newFile.txt', 'utf8');
    const isWordPresent = originalContent.includes(str);
    if(isWordPresent){
        return res.send('Element is present before, Can not add this again');
    }
    if(!checkValidation(str)){
        console.log("Error in input string! Check your input format doc");
        return res.status(400).json({
            error: 'Bad Request'
        });
    }
    else{
        try {
            const result = insertIntoSortedFile(str);
            return res.send(result);
        } catch (err) {
           console.log(">>>>>>>", err); 
           res.end('An error occured');
        }
    }

}

module.exports.readFile = function(req, res) {
    readFileContents(req, res);
}

module.exports.updateFile = function(req, res) {
    const oldString = req.params.stringToBeReplaced;
    const newString = req.params.stringToBeReplacedBy;
    console.log("OLDSTRING:", oldString);
    console.log("NEWSTRING", newString);
    const originalContent = fs.readFileSync('newFile.txt', 'utf8');
    const isWordPresent = originalContent.includes(oldString);
    let modifiedString = originalContent;
    if(isWordPresent && checkValidation(newString)){
        try {
            const regex = new RegExp(`^${oldString}$[\\r\\n]?`, 'gm');
            let modifiedString = originalContent.replace(regex, '');
            fs.writeFileSync('newFile.txt', modifiedString, 'utf8');
            insertIntoSortedFile(newString);
            return res.end("Successfully updated");
        } catch (error) {
            console.error(error);
            res.status(500).send('An error occurred');
        }
    }
    else{
        res.end('Error in validation');
    }
    
}
module.exports.deletestring = function(req, res) {
    const stringDel = req.params.stringDel;
    const originalContent = fs.readFileSync('newFile.txt', 'utf8');
    const isWordPresent = originalContent.includes(stringDel);

    let modifiedString;
    if(isWordPresent){
        try {
            const regex = new RegExp(`^${stringDel}$[\\r\\n]?`, 'gm');
            modifiedString = originalContent.replace(regex, '');

            fs.writeFile('newFile.txt', modifiedString, 'utf8', (err) => {
                if (err) {
                    console.log("********Error is: ", err);
                    res.status(500).send('Error deleteing in file');
                    return;
                }
                else {
                    return res.end("Successfully Deleted");
                }
            });
        } catch (error) {
            console.log(error);
            res.status(500).send('An error occurred');
        }
    }
    else{
        res.status(500).send('An error occurred');
    }

}
