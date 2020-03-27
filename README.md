

## Base link:
https://vanced.app/api/v1

## paths:
/
/download/:version
/changelog/:version

## / 
Gives a json array with all versions (15+) sorted from newest to oldest

Example request:
get https://vanced.app/api/v1/ 

Example Response
```json
{
"versions":[ "15.05.54", "14.21.54" ]
}
```



## /download?

Gives a json response with multiple download links decided from multiple query strings 

### Query strings
Version: version from https://vanced.app/api/v1/ get request*
Arch: arm64_v8a,  armabi_v7a, x86
Theme: dark, black
Language: Language in language code e.g. de **
Variant: nonroot, root
* can be left out, will automatically be latest if no value given
** English will always be included and will be the only one if no value or EN is given as value

Defaulted values ?arch=arm64_v8a&theme=dark&version=latest&language=EN**&variant=nonroot 

### responses
The json response has multiple download links.
base: The base download link with the corresponding theme given in the query string (from the theme chooser)
lang: The language config download link from the corresponding language query string (system language), **not included if language=EN**
lang_en: The English split config file, **Always included**
arch: The arch split config file from the corresponding arch query string


Example request
Get https://vanced.app/api/v1/downloads?version=15.05.54&arch=x86&theme=dark&language=de&variant=nonroot

Example Response
```json
{
"base": "https://vanced.app/downloads/15.05.54/nonroot/darkbase.apk",
"lang": "https://vanced.app/downloads/15.05.54/nonroot/split_config.de.apk",
"lang_en": "https://vanced.app/downloads/15.05.54/nonroot/split_config.en.apk",
"arch": "https://vanced.app/downloads/15.05.54/nonroot/config.x86.apk" 
}
```




/changelog/:version


are the nonroot and root split language configs the same?

no

all splits different
