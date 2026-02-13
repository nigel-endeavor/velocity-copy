/**
 * Base Model Interface
 *
 * All models extend this base interface.
 * Corresponds to AbstractBaseModel in Angular.
 */

/**
 * Base interface for all models
 * Contains common fields from backend
 */
export interface BaseModel {
  id?: number;
  version?: number;
}

/**
 * Helper to check if an object has an ID
 */
export function hasId(obj: BaseModel): obj is Required<BaseModel> {
  return obj.id !== undefined && obj.id !== null && obj.id > 0;
}

/**
 * Helper to check if object is new (no ID)
 */
export function isNew(obj: BaseModel): boolean {
  return !hasId(obj);
}

export default BaseModel;
