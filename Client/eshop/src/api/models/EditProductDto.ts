/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface EditProductDto
 */
export interface EditProductDto {
    /**
     * 
     * @type {string}
     * @memberof EditProductDto
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof EditProductDto
     */
    description?: string;
}

export function EditProductDtoFromJSON(json: any): EditProductDto {
    return EditProductDtoFromJSONTyped(json, false);
}

export function EditProductDtoFromJSONTyped(json: any, ignoreDiscriminator: boolean): EditProductDto {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'name': !exists(json, 'name') ? undefined : json['name'],
        'description': !exists(json, 'description') ? undefined : json['description'],
    };
}

export function EditProductDtoToJSON(value?: EditProductDto | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'name': value.name,
        'description': value.description,
    };
}

