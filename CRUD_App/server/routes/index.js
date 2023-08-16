const express = require('express');

const router = express.Router();
console.log("Router Loaded");

router.use('/file', require('./file'));
module.exports = router;