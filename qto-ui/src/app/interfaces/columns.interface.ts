export interface CommonColumn {
  name: string,
  propertyName: string,
  type: string,
  hidden: boolean,
  filterable: boolean,
  active?: boolean,
  width?: string
  filterType?: string
  wrapText?: boolean
  hasEmpty?: boolean
}
