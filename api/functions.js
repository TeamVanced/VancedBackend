module.exports.getVancedVersion = async (req, res, next) => {
    const versionID = req.params.id.replace(/_/g, ".")
    const version = await vancedModel.findOne({ id: versionID })
    if (!version)
      return res.status(404).json({ message: 'Cannot find version, does ' + versionID + ' exist in database?'})
  
    res.version = version
    next()
  }

  //sir fix