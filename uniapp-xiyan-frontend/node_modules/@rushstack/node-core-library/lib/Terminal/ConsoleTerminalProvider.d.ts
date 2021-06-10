import { ITerminalProvider, TerminalProviderSeverity } from './ITerminalProvider';
/**
 * Options to be provided to a {@link ConsoleTerminalProvider}
 *
 * @beta
 */
export interface IConsoleTerminalProviderOptions {
    /**
     * If true, print verbose logging messages
     */
    verboseEnabled: boolean;
}
/**
 * Terminal provider that prints to STDOUT (for log- and verbose-level messages) and
 * STDERR (for warning- and error-level messsages).
 *
 * @beta
 */
export declare class ConsoleTerminalProvider implements ITerminalProvider {
    /**
     * If true, verbose-level messages should be written to the console.
     */
    verboseEnabled: boolean;
    constructor(options?: Partial<IConsoleTerminalProviderOptions>);
    /**
     * {@inheritDoc ITerminalProvider.write}
     */
    write(data: string, severity: TerminalProviderSeverity): void;
    /**
     * {@inheritDoc ITerminalProvider.eolCharacter}
     */
    get eolCharacter(): string;
    /**
     * {@inheritDoc ITerminalProvider.supportsColor}
     */
    get supportsColor(): boolean;
}
//# sourceMappingURL=ConsoleTerminalProvider.d.ts.map