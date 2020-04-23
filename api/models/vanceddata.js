const mongoose = require('mongoose')
const marked = require('marked')
const createDomPurify = require('dompurify')
const { JSDOM } = require('jsdom')
const dompurify = createDomPurify(new JSDOM().window)

const apiSchema = new mongoose.Schema({
  //version number e.g 15.05.54
    id: String, 
  // installer changelog
  changelog: {
      type: String,
  },
  //website changelog
  websiteChangelogHTML: {
    type: String,
  },
  //version number + vanced e.g vanced 15.05.54
  title: {
    type: String,
  },
  //client changelog
  clientChangelog: {
    type: String,
  },
  createdAt: {
    type: Date,
    default: Date.now
  },
})

//Changes changelog to correct html for the site and automatically creates the title
apiSchema.pre('validate', function() {
    console.log('yes')
    if (this.changelog) {
      this.websiteChangelogHTML = dompurify.sanitize(marked(this.changelog))
    }
    if (this._id) {
      this.title = "Vanced " + this.id
    }
})


module.exports = mongoose.model('ApiModel', apiSchema)