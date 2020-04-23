const express = require('express');
const router = express.Router();
const vancedModel = require('./models/vanceddata');

//creating a version
router.post('/', async (req, res) => {
    const vancedVersion = new vancedModel({
      id: req.body.version,
    })
    try {
      const newVancedVersion = await vancedVersion.save()
      res.status(201).json(newVancedVersion)
    } catch (err) {
      res.status(400).json({ message: err.message })
    }
})

router.get('/', async (req, res) => {
    //okay this is broken. It should sort id from newest createdAt date, but now it does not.
    res.json({versions: await vancedModel.find().sort({ createdAt: 'desc' }).distinct('id')})
})

// Deleting version 
router.delete('/:id', getVancedVersion, async (req, res) => {
    try {
      await res.version.remove()
      res.json({ message: 'Deleted version' })
    } catch (err) {
      res.status(500).json({ message: err.message })
    }
})


async function getVancedVersion(req, res, next) {
  const versionID = req.params.id.replace(/_/g, ".")
  const version = await vancedModel.findOne({ id: versionID })
  if (!version)
    return res.status(404).json({ message: 'Cannot find version, does ' + versionID + ' exist in database?'})

  res.version = version
  next()
}

module.exports = router