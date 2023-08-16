const express = require('express');
const router = express.Router();

const filecontroller = require('../controller/file_string_crud');
router.get('/createFile', filecontroller.create_file);
router.get('/add/:stringToBeAdded', filecontroller.addString);
router.get('/getFile', filecontroller.readFile);
router.get('/update/:stringToBeReplaced/:stringToBeReplacedBy', filecontroller.updateFile);
router.get('/delete/:stringDel', filecontroller.deletestring);
module.exports = router;