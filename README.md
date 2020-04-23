
# Vanced API/backend documentation
## Base link:
https://vanced.app/api/v1

## paths:
/versions\
/download\
/changelog/:version
/microg

## /versions
Gives a json array with all versions (15+) sorted from newest to oldest

Example request:\
`get https://vanced.app/api/v1/versions`

Example Response:
```yaml
{
"versions":[ "15.05.54", "14.21.54" ]
}
```

## /download
Gives a json response with multiple download links decided from multiple query strings 

### query strings
Version: version from https://vanced.app/api/v1/ get request\
Arch: arm64_v8a,  armabi_v7a, x86 \
Theme: dark, black \
Language: Language in language code e.g. de \
Variant: nonroot, root
**Defaulted values:**\
arch=arm64_v8a&theme=dark&variant=nonroot&version=latest&language=en

**notes:**\
Version can be left out, will automatically be latest if no value given(or if value is "latest")\
English will always be included(look at responses) and will be the only one if no value or EN is given as value



### responses
The json response has multiple download links

base: The base download link with the corresponding theme given in the query string (from the theme chooser)\
lang: The language config download link from the corresponding language query string (system language), **not included if language=EN**\
lang_en: The English split config file, **always included**\
arch: The arch split config file from the corresponding arch query string

Example request:
`get https://vanced.app/api/v1/download?version=15.05.54&arch=x86&theme=dark&language=de&variant=nonroot`

Example response:
```yaml
{
"base": "https://vanced.app/downloads/15.05.54/nonroot/darkbase.apk",
"lang": "https://vanced.app/downloads/15.05.54/nonroot/split_config.de.apk",
"lang_en": "https://vanced.app/downloads/15.05.54/nonroot/split_config.en.apk",
"arch": "https://vanced.app/downloads/15.05.54/nonroot/config.x86.apk" 
}
```

## /microg
Gives an array with all MicroG versions(most likely just one, we don't update it often)

Example request:
`get https://vanced.app/api/v1/microg`

Example response:
```yaml
{
"versions":[ "0.2.6.17455", "older.version", "older.version" ]
}
```

## /download/microg
### query strings
version: a version number (if left out latest)
**Defaulted values:**\
version=latest

Example request:
`get https://vanced.app/api/v1/download/microg?version=0.2.6.17455`

Example response:
```yaml
{
"app": "https://vanced.app/downloads/microg/MicroG-0.2.6.17455.apk",
}
```


## /changelog/:version
Gives seperate changelogs for the YouTube client, website and installer.
which changelog you get is defined by a query string named "app"
the query string "app" can be 3 things: client(read notes), site or installer.

:version can be a version number from the https://vanced.app/api/v1/ get request array but can also be 'latest' to grab the newest changelog

Example request:
`get https://vanced.app/api/v1/changelog/15.05.54?app=installer`

Example response:
```yaml
{
"changelog": "MicroG - 0.2.6.17455 or higher required for login\nChangelog:\n - Search ads are still there (haven't found a way to remove them)"
}
```
**notes**:
The Vanced YouTube client always used /changelog/:version so if the 'app' query string is undefined(most cases) it will still display the correct changelog






