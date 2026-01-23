import { InputTypes } from '../enums/input-types.enum';

export interface MultiEditData {
  name: string
  fields: MultiEditField[]
}
export interface MultiEditField {
  name: string,
  value: string,
  type: InputTypes,
  disabled?: boolean,
  required?: boolean,
  select?: {
    label: string,
    value: string,
    searchBy?: string
  }
  fields?: MultiEditField[]
}
