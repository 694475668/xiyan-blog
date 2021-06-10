"use strict";
// Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT license.
// See LICENSE in the project root for license information.
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    Object.defineProperty(o, k2, { enumerable: true, get: function() { return m[k]; } });
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.DtsEmitHelpers = void 0;
const ts = __importStar(require("typescript"));
const node_core_library_1 = require("@rushstack/node-core-library");
const AstImport_1 = require("../analyzer/AstImport");
/**
 * Some common code shared between DtsRollupGenerator and ApiReportGenerator.
 */
class DtsEmitHelpers {
    static emitImport(stringWriter, collectorEntity, astImport) {
        const importPrefix = astImport.isTypeOnlyEverywhere ? 'import type' : 'import';
        switch (astImport.importKind) {
            case AstImport_1.AstImportKind.DefaultImport:
                if (collectorEntity.nameForEmit !== astImport.exportName) {
                    stringWriter.write(`${importPrefix} { default as ${collectorEntity.nameForEmit} }`);
                }
                else {
                    stringWriter.write(`${importPrefix} ${astImport.exportName}`);
                }
                stringWriter.writeLine(` from '${astImport.modulePath}';`);
                break;
            case AstImport_1.AstImportKind.NamedImport:
                if (collectorEntity.nameForEmit !== astImport.exportName) {
                    stringWriter.write(`${importPrefix} { ${astImport.exportName} as ${collectorEntity.nameForEmit} }`);
                }
                else {
                    stringWriter.write(`${importPrefix} { ${astImport.exportName} }`);
                }
                stringWriter.writeLine(` from '${astImport.modulePath}';`);
                break;
            case AstImport_1.AstImportKind.StarImport:
                stringWriter.writeLine(`${importPrefix} * as ${collectorEntity.nameForEmit} from '${astImport.modulePath}';`);
                break;
            case AstImport_1.AstImportKind.EqualsImport:
                stringWriter.writeLine(`${importPrefix} ${collectorEntity.nameForEmit} = require('${astImport.modulePath}');`);
                break;
            default:
                throw new node_core_library_1.InternalError('Unimplemented AstImportKind');
        }
    }
    static emitNamedExport(stringWriter, exportName, collectorEntity) {
        if (exportName === ts.InternalSymbolName.Default) {
            stringWriter.writeLine(`export default ${collectorEntity.nameForEmit};`);
        }
        else if (collectorEntity.nameForEmit !== exportName) {
            stringWriter.writeLine(`export { ${collectorEntity.nameForEmit} as ${exportName} }`);
        }
        else {
            stringWriter.writeLine(`export { ${exportName} }`);
        }
    }
    static emitStarExports(stringWriter, collector) {
        if (collector.starExportedExternalModulePaths.length > 0) {
            stringWriter.writeLine();
            for (const starExportedExternalModulePath of collector.starExportedExternalModulePaths) {
                stringWriter.writeLine(`export * from "${starExportedExternalModulePath}";`);
            }
        }
    }
}
exports.DtsEmitHelpers = DtsEmitHelpers;
//# sourceMappingURL=DtsEmitHelpers.js.map