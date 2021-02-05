const Ajv = require("ajv").default
const fs = require('fs');

const ajv = new Ajv() // options can be passed, e.g. {allErrors: true}
const schema = JSON.parse(fs.readFileSync("./schemas/schema.json", "utf8"));

test('Validate troubleshooting.json schema', () => {
    var data = JSON.parse(fs.readFileSync("./strings/en/troubleshooting.json", "utf8"));

    const validate = ajv.compile(schema)
    expect(validate(data)).toBe(true);
});

test('Validate features.json schema', () => {
    var data = JSON.parse(fs.readFileSync("./strings/en/features.json", "utf8"));

    const validate = ajv.compile(schema)
    expect(validate(data)).toBe(true);
});

test('Validate faq.json schema', () => {
    var data = JSON.parse(fs.readFileSync("./strings/en/faq.json", "utf8"));

    const validate = ajv.compile(schema)
    expect(validate(data)).toBe(true);
});

test('Validate bugreport.json schema', () => {
    var data = JSON.parse(fs.readFileSync("./strings/en/bugreport.json", "utf8"));

    const validate = ajv.compile(schema)
    expect(validate(data)).toBe(true);
});

// test('Validate site.json schema', () => {
//     var data = JSON.parse(fs.readFileSync("./strings/en/site.json", "utf8"));

//     const validate = ajv.compile(schema)
//     expect(validate(data)).toBe(true);
// });