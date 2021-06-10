"use strict";
// Copyright (c) Microsoft Corporation. All rights reserved. Licensed under the MIT license.
// See LICENSE in the project root for license information.
Object.defineProperty(exports, "__esModule", { value: true });
exports.ApiItem = exports.apiItem_onParentChanged = void 0;
const ApiParameterListMixin_1 = require("../mixins/ApiParameterListMixin");
const node_core_library_1 = require("@rushstack/node-core-library");
const ApiItemContainerMixin_1 = require("../mixins/ApiItemContainerMixin");
// PRIVATE - Allows ApiItemContainerMixin to assign the parent.
//
exports.apiItem_onParentChanged = Symbol('ApiItem._onAddToContainer');
/**
 * The abstract base class for all members of an `ApiModel` object.
 *
 * @remarks
 * This is part of the {@link ApiModel} hierarchy of classes, which are serializable representations of
 * API declarations.
 * @public
 */
class ApiItem {
    constructor(options) {
        // ("options" is not used here, but part of the inheritance pattern)
    }
    static deserialize(jsonObject, context) {
        // The Deserializer class is coupled with a ton of other classes, so  we delay loading it
        // to avoid ES5 circular imports.
        const deserializerModule = require('../model/Deserializer');
        return deserializerModule.Deserializer.deserialize(context, jsonObject);
    }
    /** @virtual */
    static onDeserializeInto(options, context, jsonObject) {
        // (implemented by subclasses)
    }
    /** @virtual */
    serializeInto(jsonObject) {
        jsonObject.kind = this.kind;
        jsonObject.canonicalReference = this.canonicalReference.toString();
    }
    /**
     * Identifies the subclass of the `ApiItem` base class.
     * @virtual
     */
    get kind() {
        throw new Error('ApiItem.kind was not implemented by the child class');
    }
    /**
     * Warning: This API is used internally by API extractor but is not yet ready for general usage.
     *
     * @remarks
     *
     * Returns a `DeclarationReference` object using the experimental new declaration reference notation.
     *
     * @beta
     */
    get canonicalReference() {
        if (!this._canonicalReference) {
            try {
                this._canonicalReference = this.buildCanonicalReference();
            }
            catch (e) {
                const name = this.getScopedNameWithinPackage() || this.displayName;
                throw new node_core_library_1.InternalError(`Error building canonical reference for ${name}:\n` + e.message);
            }
        }
        return this._canonicalReference;
    }
    /**
     * Returns a string key that can be used to efficiently retrieve an `ApiItem` from an `ApiItemContainerMixin`.
     * The key is unique within the container.  Its format is undocumented and may change at any time.
     *
     * @remarks
     * Use the `getContainerKey()` static member to construct the key.  Each subclass has a different implementation
     * of this function, according to the aspects that are important for identifying it.
     *
     * @virtual
     */
    get containerKey() {
        throw new node_core_library_1.InternalError('ApiItem.containerKey was not implemented by the child class');
    }
    /**
     * Returns a name for this object that can be used in diagnostic messages, for example.
     *
     * @remarks
     * For an object that inherits ApiNameMixin, this will return the declared name (e.g. the name of a TypeScript
     * function).  Otherwise, it will return a string such as "(call signature)" or "(model)".
     *
     * @virtual
     */
    get displayName() {
        switch (this.kind) {
            case "CallSignature" /* CallSignature */:
                return '(call)';
            case "Constructor" /* Constructor */:
                return '(constructor)';
            case "ConstructSignature" /* ConstructSignature */:
                return '(new)';
            case "IndexSignature" /* IndexSignature */:
                return '(indexer)';
            case "Model" /* Model */:
                return '(model)';
        }
        return '(???)'; // All other types should inherit ApiNameMixin which will override this property
    }
    /**
     * If this item was added to a ApiItemContainerMixin item, then this returns the container item.
     * If this is an Parameter that was added to a method or function, then this returns the function item.
     * Otherwise, it returns undefined.
     * @virtual
     */
    get parent() {
        return this._parent;
    }
    /**
     * This property supports a visitor pattern for walking the tree.
     * For items with ApiItemContainerMixin, it returns the contained items, sorted alphabetically.
     * Otherwise it returns an empty array.
     * @virtual
     */
    get members() {
        return [];
    }
    /**
     * If this item has a name (i.e. extends `ApiNameMixin`), then return all items that have the same parent
     * and the same name.  Otherwise, return all items that have the same parent and the same `ApiItemKind`.
     *
     * @remarks
     * Examples: For a function, this would return all overloads for the function.  For a constructor, this would
     * return all overloads for the constructor.  For a merged declaration (e.g. a `namespace` and `enum` with the
     * same name), this would return both declarations.  If this item does not have a parent, or if it is the only
     * item of its name/kind, then the result is an array containing only this item.
     */
    getMergedSiblings() {
        const parent = this._parent;
        if (parent && ApiItemContainerMixin_1.ApiItemContainerMixin.isBaseClassOf(parent)) {
            return parent._getMergedSiblingsForMember(this);
        }
        return [];
    }
    /**
     * Returns the chain of ancestors, starting from the root of the tree, and ending with the this item.
     */
    getHierarchy() {
        const hierarchy = [];
        for (let current = this; current !== undefined; current = current.parent) {
            hierarchy.push(current);
        }
        hierarchy.reverse();
        return hierarchy;
    }
    /**
     * This returns a scoped name such as `"Namespace1.Namespace2.MyClass.myMember()"`.  It does not include the
     * package name or entry point.
     *
     * @remarks
     * If called on an ApiEntrypoint, ApiPackage, or ApiModel item, the result is an empty string.
     */
    getScopedNameWithinPackage() {
        const reversedParts = [];
        for (let current = this; current !== undefined; current = current.parent) {
            if (current.kind === "Model" /* Model */ ||
                current.kind === "Package" /* Package */ ||
                current.kind === "EntryPoint" /* EntryPoint */) {
                break;
            }
            if (reversedParts.length !== 0) {
                reversedParts.push('.');
            }
            else {
                switch (current.kind) {
                    case "CallSignature" /* CallSignature */:
                    case "ConstructSignature" /* ConstructSignature */:
                    case "Constructor" /* Constructor */:
                    case "IndexSignature" /* IndexSignature */:
                        // These functional forms don't have a proper name, so we don't append the "()" suffix
                        break;
                    default:
                        if (ApiParameterListMixin_1.ApiParameterListMixin.isBaseClassOf(current)) {
                            reversedParts.push('()');
                        }
                }
            }
            reversedParts.push(current.displayName);
        }
        return reversedParts.reverse().join('');
    }
    /**
     * If this item is an ApiPackage or has an ApiPackage as one of its parents, then that object is returned.
     * Otherwise undefined is returned.
     */
    getAssociatedPackage() {
        for (let current = this; current !== undefined; current = current.parent) {
            if (current.kind === "Package" /* Package */) {
                return current;
            }
        }
        return undefined;
    }
    /** @virtual */
    getSortKey() {
        return this.containerKey;
    }
    /**
     * PRIVATE
     *
     * @privateRemarks
     * Allows ApiItemContainerMixin to assign the parent when the item is added to a container.
     *
     * @internal
     */
    [exports.apiItem_onParentChanged](parent) {
        this._parent = parent;
        this._canonicalReference = undefined;
    }
    /**
     * Builds the cached object used by the `canonicalReference` property.
     * @virtual
     */
    buildCanonicalReference() {
        throw new node_core_library_1.InternalError('ApiItem.canonicalReference was not implemented by the child class');
    }
}
exports.ApiItem = ApiItem;
//# sourceMappingURL=ApiItem.js.map