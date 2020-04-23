const express = require('express');
const router = express.Router();
const vancedModel = require('./models/vanceddata');

//creating a changelog to a version (NOT FINISHED KEEP YOUR HANDS OF THIS CODE YOU FILTHY BASTARD)
router.post('/:id', getVancedVersion, async (req, res) => {
    const vancedVersion = new vancedModel({
      id: req.body.version,
    })
    try {
      const newVancedVersion = await vancedVersion.save()
      res.status(201).json(newVancedVersion)
    } catch (err) {
      res.status(400).json({ message: err.message })
    }
    res.version
})

router.get('/:id', getVancedVersion, async (req, res) => {
    if (req.query.app == 'client' || req.query.app == null) {
    res.json({message: res.version.clientChangelog, title: res.version.title, buttonpositive: 'Dissmiss', buttonnegative: 'Later'})
    } if (req.query.app == 'installer') {
        res.json()
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