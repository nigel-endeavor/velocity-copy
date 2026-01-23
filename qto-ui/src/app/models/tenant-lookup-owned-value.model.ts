import { AbstractBaseModel } from "./abstract-base-model";
import { LookupValue } from "./lookup-value.model";

export class TenantOwnedLookupValue extends LookupValue {
  tenantLookupValues: TenantLookupValue[];
  configured: boolean;
}

export class TenantLookupValue extends AbstractBaseModel {
  lookupValue: LookupValue;
}