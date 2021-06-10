"use strict";
// Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT license.
// See LICENSE in the project root for license information.
Object.defineProperty(exports, "__esModule", { value: true });
exports.CommandLineStringListParameter = void 0;
const BaseClasses_1 = require("./BaseClasses");
/**
 * The data type returned by {@link CommandLineParameterProvider.defineStringListParameter}.
 * @public
 */
class CommandLineStringListParameter extends BaseClasses_1.CommandLineParameterWithArgument {
    /** @internal */
    constructor(definition) {
        super(definition);
        this._values = [];
    }
    /** {@inheritDoc CommandLineParameter.kind} */
    get kind() {
        return BaseClasses_1.CommandLineParameterKind.StringList;
    }
    /**
     * {@inheritDoc CommandLineParameter._setValue}
     * @internal
     */
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    _setValue(data) {
        // abstract
        if (data !== null && data !== undefined) {
            if (!Array.isArray(data)) {
                this.reportInvalidData(data);
            }
            for (const arrayItem of data) {
                if (typeof arrayItem !== 'string') {
                    this.reportInvalidData(data);
                }
            }
            this._values = data;
            return;
        }
        if (this.environmentVariable !== undefined) {
            // Try reading the environment variable
            const environmentValue = process.env[this.environmentVariable];
            if (environmentValue !== undefined) {
                // NOTE: If the environment variable is defined as an empty string,
                // here we will accept the empty string as our value.  (For number/flag we don't do that.)
                if (environmentValue.trimLeft()[0] === '[') {
                    // Specifying multiple items in an environment variable is a somewhat rare case.  But environment
                    // variables are actually a pretty reliable way for a tool to avoid shell escaping problems
                    // when spawning another tool.  For this case, we need a reliable way to pass an array of strings
                    // that could contain any character.  For example, if we simply used ";" as the list delimiter,
                    // then what to do if a string contains that character?  We'd need to design an escaping mechanism.
                    // Since JSON is simple and standard and can escape every possible string, it's a better option
                    // than a custom delimiter.
                    try {
                        const parsedJson = JSON.parse(environmentValue);
                        if (!Array.isArray(parsedJson) ||
                            !parsedJson.every((x) => typeof x === 'string' || typeof x === 'boolean' || typeof x === 'number')) {
                            throw new Error(`The ${environmentValue} environment variable value must be a JSON ` +
                                ` array containing only strings, numbers, and booleans.`);
                        }
                        this._values = parsedJson.map((x) => x.toString());
                    }
                    catch (ex) {
                        throw new Error(`The ${environmentValue} environment variable value looks like a JSON array` +
                            ` but failed to parse: ` +
                            ex.message);
                    }
                }
                else {
                    // As a shorthand, a single value may be specified without JSON encoding, as long as it does not
                    // start with the "[" character.
                    this._values = [environmentValue];
                }
                return;
            }
        }
        // (No default value for string lists)
        this._values = [];
    }
    /**
     * Returns the string arguments for a string list parameter that was parsed from the command line.
     *
     * @remarks
     * The array will be empty if the command-line has not been parsed yet,
     * or if the parameter was omitted and has no default value.
     */
    get values() {
        return this._values;
    }
    /** {@inheritDoc CommandLineParameter.appendToArgList} @override */
    appendToArgList(argList) {
        if (this.values.length > 0) {
            for (const value of this.values) {
                argList.push(this.longName);
                argList.push(value);
            }
        }
    }
}
exports.CommandLineStringListParameter = CommandLineStringListParameter;
//# sourceMappingURL=CommandLineStringListParameter.js.map