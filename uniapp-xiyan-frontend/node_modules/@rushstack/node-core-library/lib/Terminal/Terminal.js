"use strict";
// Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT license.
// See LICENSE in the project root for license information.
Object.defineProperty(exports, "__esModule", { value: true });
exports.Terminal = void 0;
const ITerminalProvider_1 = require("./ITerminalProvider");
const Colors_1 = require("./Colors");
/**
 * This class facilitates writing to a console.
 *
 * @beta
 */
class Terminal {
    constructor(provider) {
        this._providers = new Set();
        this._providers.add(provider);
    }
    /**
     * Subscribe a new terminal provider.
     */
    registerProvider(provider) {
        this._providers.add(provider);
    }
    /**
     * Unsubscribe a terminal provider. If the provider isn't subscribed, this function does nothing.
     */
    unregisterProvider(provider) {
        if (this._providers.has(provider)) {
            this._providers.delete(provider);
        }
    }
    /**
     * Write a generic message to the terminal
     */
    write(...messageParts) {
        this._writeSegmentsToProviders(messageParts, ITerminalProvider_1.TerminalProviderSeverity.log);
    }
    /**
     * Write a generic message to the terminal, followed by a newline
     */
    writeLine(...messageParts) {
        this.write(...messageParts, Colors_1.eolSequence);
    }
    /**
     * Write a warning message to the console with yellow text.
     *
     * @remarks
     * The yellow color takes precedence over any other foreground colors set.
     */
    writeWarning(...messageParts) {
        this._writeSegmentsToProviders(messageParts.map((part) => (Object.assign(Object.assign({}, Colors_1.Colors._normalizeStringOrColorableSequence(part)), { foregroundColor: Colors_1.ColorValue.Yellow }))), ITerminalProvider_1.TerminalProviderSeverity.warning);
    }
    /**
     * Write a warning message to the console with yellow text, followed by a newline.
     *
     * @remarks
     * The yellow color takes precedence over any other foreground colors set.
     */
    writeWarningLine(...messageParts) {
        this._writeSegmentsToProviders([
            ...messageParts.map((part) => (Object.assign(Object.assign({}, Colors_1.Colors._normalizeStringOrColorableSequence(part)), { foregroundColor: Colors_1.ColorValue.Yellow }))),
            Colors_1.eolSequence
        ], ITerminalProvider_1.TerminalProviderSeverity.warning);
    }
    /**
     * Write an error message to the console with red text.
     *
     * @remarks
     * The red color takes precedence over any other foreground colors set.
     */
    writeError(...messageParts) {
        this._writeSegmentsToProviders(messageParts.map((part) => (Object.assign(Object.assign({}, Colors_1.Colors._normalizeStringOrColorableSequence(part)), { foregroundColor: Colors_1.ColorValue.Red }))), ITerminalProvider_1.TerminalProviderSeverity.error);
    }
    /**
     * Write an error message to the console with red text, followed by a newline.
     *
     * @remarks
     * The red color takes precedence over any other foreground colors set.
     */
    writeErrorLine(...messageParts) {
        this._writeSegmentsToProviders([
            ...messageParts.map((part) => (Object.assign(Object.assign({}, Colors_1.Colors._normalizeStringOrColorableSequence(part)), { foregroundColor: Colors_1.ColorValue.Red }))),
            Colors_1.eolSequence
        ], ITerminalProvider_1.TerminalProviderSeverity.error);
    }
    /**
     * Write a verbose-level message.
     */
    writeVerbose(...messageParts) {
        this._writeSegmentsToProviders(messageParts, ITerminalProvider_1.TerminalProviderSeverity.verbose);
    }
    /**
     * Write a verbose-level message followed by a newline.
     */
    writeVerboseLine(...messageParts) {
        this.writeVerbose(...messageParts, Colors_1.eolSequence);
    }
    _writeSegmentsToProviders(segments, severity) {
        const withColorText = {};
        const withoutColorText = {};
        let withColorLines;
        let withoutColorLines;
        this._providers.forEach((provider) => {
            const eol = provider.eolCharacter;
            let textToWrite;
            if (provider.supportsColor) {
                if (!withColorLines) {
                    withColorLines = this._serializeFormattableTextSegments(segments, true);
                }
                if (!withColorText[eol]) {
                    withColorText[eol] = withColorLines.join(eol);
                }
                textToWrite = withColorText[eol];
            }
            else {
                if (!withoutColorLines) {
                    withoutColorLines = this._serializeFormattableTextSegments(segments, false);
                }
                if (!withoutColorText[eol]) {
                    withoutColorText[eol] = withoutColorLines.join(eol);
                }
                textToWrite = withoutColorText[eol];
            }
            provider.write(textToWrite, severity);
        });
    }
    _serializeFormattableTextSegments(segments, withColor) {
        const lines = [];
        let segmentsToJoin = [];
        let lastSegmentWasEol = false;
        for (let i = 0; i < segments.length; i++) {
            const segment = Colors_1.Colors._normalizeStringOrColorableSequence(segments[i]);
            lastSegmentWasEol = !!segment.isEol;
            if (lastSegmentWasEol) {
                lines.push(segmentsToJoin.join(''));
                segmentsToJoin = [];
            }
            else {
                if (withColor) {
                    const startColorCodes = [];
                    const endColorCodes = [];
                    switch (segment.foregroundColor) {
                        case Colors_1.ColorValue.Black: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.BlackForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.Red: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.RedForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.Green: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.GreenForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.Yellow: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.YellowForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.Blue: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.BlueForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.Magenta: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.MagentaForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.Cyan: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.CyanForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.White: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.WhiteForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                        case Colors_1.ColorValue.Gray: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.GrayForeground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultForeground);
                            break;
                        }
                    }
                    switch (segment.backgroundColor) {
                        case Colors_1.ColorValue.Black: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.BlackBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.Red: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.RedBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.Green: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.GreenBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.Yellow: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.YellowBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.Blue: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.BlueBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.Magenta: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.MagentaBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.Cyan: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.CyanBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.White: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.WhiteBackground);
                            endColorCodes.push(Colors_1.ConsoleColorCodes.DefaultBackground);
                            break;
                        }
                        case Colors_1.ColorValue.Gray: {
                            startColorCodes.push(Colors_1.ConsoleColorCodes.GrayBackground);
                            endColorCodes.push(49);
                            break;
                        }
                    }
                    if (segment.textAttributes) {
                        for (const textAttribute of segment.textAttributes) {
                            switch (textAttribute) {
                                case Colors_1.TextAttribute.Bold: {
                                    startColorCodes.push(Colors_1.ConsoleColorCodes.Bold);
                                    endColorCodes.push(Colors_1.ConsoleColorCodes.NormalColorOrIntensity);
                                    break;
                                }
                                case Colors_1.TextAttribute.Dim: {
                                    startColorCodes.push(Colors_1.ConsoleColorCodes.Dim);
                                    endColorCodes.push(Colors_1.ConsoleColorCodes.NormalColorOrIntensity);
                                    break;
                                }
                                case Colors_1.TextAttribute.Underline: {
                                    startColorCodes.push(Colors_1.ConsoleColorCodes.Underline);
                                    endColorCodes.push(Colors_1.ConsoleColorCodes.UnderlineOff);
                                    break;
                                }
                                case Colors_1.TextAttribute.Blink: {
                                    startColorCodes.push(Colors_1.ConsoleColorCodes.Blink);
                                    endColorCodes.push(Colors_1.ConsoleColorCodes.BlinkOff);
                                    break;
                                }
                                case Colors_1.TextAttribute.InvertColor: {
                                    startColorCodes.push(Colors_1.ConsoleColorCodes.InvertColor);
                                    endColorCodes.push(Colors_1.ConsoleColorCodes.InvertColorOff);
                                    break;
                                }
                                case Colors_1.TextAttribute.Hidden: {
                                    startColorCodes.push(Colors_1.ConsoleColorCodes.Hidden);
                                    endColorCodes.push(Colors_1.ConsoleColorCodes.HiddenOff);
                                    break;
                                }
                            }
                        }
                    }
                    for (let j = 0; j < startColorCodes.length; j++) {
                        const code = startColorCodes[j];
                        segmentsToJoin.push(...['\u001b[', code.toString(), 'm']);
                    }
                    segmentsToJoin.push(segment.text);
                    for (let j = endColorCodes.length - 1; j >= 0; j--) {
                        const code = endColorCodes[j];
                        segmentsToJoin.push(...['\u001b[', code.toString(), 'm']);
                    }
                }
                else {
                    segmentsToJoin.push(segment.text);
                }
            }
        }
        if (segmentsToJoin.length > 0) {
            lines.push(segmentsToJoin.join(''));
        }
        if (lastSegmentWasEol) {
            lines.push('');
        }
        return lines;
    }
}
exports.Terminal = Terminal;
//# sourceMappingURL=Terminal.js.map