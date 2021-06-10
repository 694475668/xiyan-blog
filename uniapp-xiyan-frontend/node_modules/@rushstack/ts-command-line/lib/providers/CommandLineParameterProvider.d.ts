import * as argparse from 'argparse';
import { ICommandLineFlagDefinition, ICommandLineStringDefinition, ICommandLineStringListDefinition, ICommandLineIntegerDefinition, ICommandLineChoiceDefinition, ICommandLineRemainderDefinition } from '../parameters/CommandLineDefinition';
import { CommandLineParameter } from '../parameters/BaseClasses';
import { CommandLineFlagParameter } from '../parameters/CommandLineFlagParameter';
import { CommandLineStringParameter } from '../parameters/CommandLineStringParameter';
import { CommandLineStringListParameter } from '../parameters/CommandLineStringListParameter';
import { CommandLineIntegerParameter } from '../parameters/CommandLineIntegerParameter';
import { CommandLineChoiceParameter } from '../parameters/CommandLineChoiceParameter';
import { CommandLineRemainder } from '../parameters/CommandLineRemainder';
/**
 * This is the argparse result data object
 * @internal
 */
export interface ICommandLineParserData {
    action: string;
    [key: string]: any;
}
/**
 * This is the common base class for CommandLineAction and CommandLineParser
 * that provides functionality for defining command-line parameters.
 *
 * @public
 */
export declare abstract class CommandLineParameterProvider {
    private static _keyCounter;
    private _parameters;
    private _parametersByLongName;
    private _remainder;
    /** @internal */
    constructor();
    /**
     * Returns a collection of the parameters that were defined for this object.
     */
    get parameters(): ReadonlyArray<CommandLineParameter>;
    /**
     * If {@link CommandLineParameterProvider.defineCommandLineRemainder} was called,
     * this object captures any remaining command line arguments after the recognized portion.
     */
    get remainder(): CommandLineRemainder | undefined;
    /**
     * Defines a command-line parameter whose value must be a string from a fixed set of
     * allowable choices (similar to an enum).
     *
     * @remarks
     * Example of a choice parameter:
     * ```
     * example-tool --log-level warn
     * ```
     */
    defineChoiceParameter(definition: ICommandLineChoiceDefinition): CommandLineChoiceParameter;
    /**
     * Returns the CommandLineChoiceParameter with the specified long name.
     * @remarks
     * This method throws an exception if the parameter is not defined.
     */
    getChoiceParameter(parameterLongName: string): CommandLineChoiceParameter;
    /**
     * Defines a command-line switch whose boolean value is true if the switch is provided,
     * and false otherwise.
     *
     * @remarks
     * Example usage of a flag parameter:
     * ```
     * example-tool --debug
     * ```
     */
    defineFlagParameter(definition: ICommandLineFlagDefinition): CommandLineFlagParameter;
    /**
     * Returns the CommandLineFlagParameter with the specified long name.
     * @remarks
     * This method throws an exception if the parameter is not defined.
     */
    getFlagParameter(parameterLongName: string): CommandLineFlagParameter;
    /**
     * Defines a command-line parameter whose argument is an integer.
     *
     * @remarks
     * Example usage of an integer parameter:
     * ```
     * example-tool --max-attempts 5
     * ```
     */
    defineIntegerParameter(definition: ICommandLineIntegerDefinition): CommandLineIntegerParameter;
    /**
     * Returns the CommandLineIntegerParameter with the specified long name.
     * @remarks
     * This method throws an exception if the parameter is not defined.
     */
    getIntegerParameter(parameterLongName: string): CommandLineIntegerParameter;
    /**
     * Defines a command-line parameter whose argument is a single text string.
     *
     * @remarks
     * Example usage of a string parameter:
     * ```
     * example-tool --message "Hello, world!"
     * ```
     */
    defineStringParameter(definition: ICommandLineStringDefinition): CommandLineStringParameter;
    /**
     * Returns the CommandLineStringParameter with the specified long name.
     * @remarks
     * This method throws an exception if the parameter is not defined.
     */
    getStringParameter(parameterLongName: string): CommandLineStringParameter;
    /**
     * Defines a command-line parameter whose argument is a single text string.  The parameter can be
     * specified multiple times to build a list.
     *
     * @remarks
     * Example usage of a string list parameter:
     * ```
     * example-tool --add file1.txt --add file2.txt --add file3.txt
     * ```
     */
    defineStringListParameter(definition: ICommandLineStringListDefinition): CommandLineStringListParameter;
    /**
     * Defines a rule that captures any remaining command line arguments after the recognized portion.
     *
     * @remarks
     * This feature is useful for commands that pass their arguments along to an external tool, relying on
     * that tool to perform validation.  (It could also be used to parse parameters without any validation
     * or documentation, but that is not recommended.)
     *
     * Example of capturing the remainder after an optional flag parameter.
     * ```
     * example-tool --my-flag this is the remainder
     * ```
     *
     * In the "--help" documentation, the remainder rule will be represented as "...".
     */
    defineCommandLineRemainder(definition: ICommandLineRemainderDefinition): CommandLineRemainder;
    /**
     * Returns the CommandLineStringListParameter with the specified long name.
     * @remarks
     * This method throws an exception if the parameter is not defined.
     */
    getStringListParameter(parameterLongName: string): CommandLineStringListParameter;
    /**
     * Generates the command-line help text.
     */
    renderHelpText(): string;
    /**
     * The child class should implement this hook to define its command-line parameters,
     * e.g. by calling defineFlagParameter().
     */
    protected abstract onDefineParameters(): void;
    /**
     * Retrieves the argparse object.
     * @internal
     */
    protected abstract _getArgumentParser(): argparse.ArgumentParser;
    /** @internal */
    protected _processParsedData(data: ICommandLineParserData): void;
    private _generateKey;
    private _getParameter;
    private _defineParameter;
}
//# sourceMappingURL=CommandLineParameterProvider.d.ts.map