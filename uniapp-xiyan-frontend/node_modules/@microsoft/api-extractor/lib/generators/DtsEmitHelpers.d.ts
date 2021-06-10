import { CollectorEntity } from '../collector/CollectorEntity';
import { AstImport } from '../analyzer/AstImport';
import { StringWriter } from './StringWriter';
import { Collector } from '../collector/Collector';
/**
 * Some common code shared between DtsRollupGenerator and ApiReportGenerator.
 */
export declare class DtsEmitHelpers {
    static emitImport(stringWriter: StringWriter, collectorEntity: CollectorEntity, astImport: AstImport): void;
    static emitNamedExport(stringWriter: StringWriter, exportName: string, collectorEntity: CollectorEntity): void;
    static emitStarExports(stringWriter: StringWriter, collector: Collector): void;
}
//# sourceMappingURL=DtsEmitHelpers.d.ts.map