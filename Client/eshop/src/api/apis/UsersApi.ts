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


import * as runtime from '../runtime';
import {
    AuthDto,
    AuthDtoFromJSON,
    AuthDtoToJSON,
    AuthRequestDto,
    AuthRequestDtoFromJSON,
    AuthRequestDtoToJSON,
    CreateUserDto,
    CreateUserDtoFromJSON,
    CreateUserDtoToJSON,
    UserDto,
    UserDtoFromJSON,
    UserDtoToJSON,
} from '../models';

export interface AuthenticateRequest {
    authRequestDto: AuthRequestDto;
}

export interface CreateUserRequest {
    createUserDto: CreateUserDto;
}

/**
 * 
 */
export class UsersApi extends runtime.BaseAPI {

    /**
     * Authenticate
     * Authenticate
     */
    async authenticateRaw(requestParameters: AuthenticateRequest, initOverrides?: RequestInit): Promise<runtime.ApiResponse<AuthDto>> {
        if (requestParameters.authRequestDto === null || requestParameters.authRequestDto === undefined) {
            throw new runtime.RequiredError('authRequestDto','Required parameter requestParameters.authRequestDto was null or undefined when calling authenticate.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/api/user/authenticate`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: AuthRequestDtoToJSON(requestParameters.authRequestDto),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => AuthDtoFromJSON(jsonValue));
    }

    /**
     * Authenticate
     * Authenticate
     */
    async authenticate(requestParameters: AuthenticateRequest, initOverrides?: RequestInit): Promise<AuthDto> {
        const response = await this.authenticateRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Create user
     * Create user
     */
    async createUserRaw(requestParameters: CreateUserRequest, initOverrides?: RequestInit): Promise<runtime.ApiResponse<UserDto>> {
        if (requestParameters.createUserDto === null || requestParameters.createUserDto === undefined) {
            throw new runtime.RequiredError('createUserDto','Required parameter requestParameters.createUserDto was null or undefined when calling createUser.');
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/api/user/createUser`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: CreateUserDtoToJSON(requestParameters.createUserDto),
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => UserDtoFromJSON(jsonValue));
    }

    /**
     * Create user
     * Create user
     */
    async createUser(requestParameters: CreateUserRequest, initOverrides?: RequestInit): Promise<UserDto> {
        const response = await this.createUserRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     * Get user
     * Get user
     */
    async getUserRaw(initOverrides?: RequestInit): Promise<runtime.ApiResponse<UserDto>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        if (this.configuration && this.configuration.accessToken) {
            const token = this.configuration.accessToken;
            const tokenString = await token("bearerAuth", []);

            if (tokenString) {
                headerParameters["Authorization"] = `Bearer ${tokenString}`;
            }
        }
        const response = await this.request({
            path: `/api/user/getUser`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => UserDtoFromJSON(jsonValue));
    }

    /**
     * Get user
     * Get user
     */
    async getUser(initOverrides?: RequestInit): Promise<UserDto> {
        const response = await this.getUserRaw(initOverrides);
        return await response.value();
    }

}
