const express = require('express')
const router = express.Router()
const bodyParser = require('body-parser')

//view engine ejs
app.set('view engine', 'ejs')
app.use(bodyParser.urlencoded({
    extended: true}));

router.get('/', async (req, res) => {
    res.render('new')
})

module.exports = router