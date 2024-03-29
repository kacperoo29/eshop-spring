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
 * @interface AuthRequestDto
 */
export interface AuthRequestDto {
    /**
     * 
     * @type {string}
     * @memberof AuthRequestDto
     */
    email?: string;
    /**
     * 
     * @type {string}
     * @memberof AuthRequestDto
     */
    password?: string;
}

export function AuthRequestDtoFromJSON(json: any): AuthRequestDto {
    return AuthRequestDtoFromJSONTyped(json, false);
}

export function AuthRequestDtoFromJSONTyped(json: any, ignoreDiscriminator: boolean): AuthRequestDto {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'email': !exists(json, 'email') ? undefined : json['email'],
        'password': !exists(json, 'password') ? undefined : json['password'],
    };
}

export function AuthRequestDtoToJSON(value?: AuthRequestDto | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'email': value.email,
        'password': value.password,
    };
}

