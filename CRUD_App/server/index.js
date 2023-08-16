const express = require('express');
const app = express();
const port = 3000;

app.get('/', (req, res) => {
    res.send('Hello, this is your Node.js server!');
});

app.use('/', require('./routes'));

app.listen(port, (err) => {
    if(err){
        console.log(`Error in creating server: ${err}`);
    }
    console.log(`Server is running on http://localhost:${port}`);
});
