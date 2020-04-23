const express = require('express')
const app = express()
const mongoose = require('mongoose');

//setting up database
mongoose.connect('mongodb://localhost:27017/api', { useNewUrlParser: true, useUnifiedTopology: true })
const db = mongoose.connection;
db.on('error', (error) => console.error(error))
db.once('open', () => console.log('Connected to Database'))

// app.get('/api/v1/site/edit', async (req, res) => {
//     const articles = await Article.find().sort({ createdAt: 'desc' })
//     res.render('api/index', { articles: articles })
//   })

app.use(express.json())

// app.use('/', require('./website/index'))
app.use('/api/v1/versions/', require('./api/vancedversions'))
app.use('/api/v1/changelog/', require('./api/vancedchangelog'))


const port = 4000
app.listen(port, () => console.log('Server Started on port' + port))