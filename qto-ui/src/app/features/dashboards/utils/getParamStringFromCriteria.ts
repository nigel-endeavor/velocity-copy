import { DashboardSearchCriteria } from "../models/dashboard-search-criteria.model";

export function getParamStringFromCriteria(criteria: DashboardSearchCriteria) {
  criteria.tenantNames = criteria.tenantNames.map(item => item.trim()).filter((value) => value !== '');
  criteria.masterCompanyNames = criteria.masterCompanyNames.map(item => item.trim()).filter((value) => value !== '');
  criteria.companyNames = criteria.companyNames.map(item => item.trim()).filter((value) => value !== '');
  criteria.serviceTypes = criteria.serviceTypes.map(item => item.trim()).filter((value) => value !== '');
  criteria.providers = criteria.providers.map(item => item.trim()).map(item => item === 'Empty' ? 'ISEMPTY' : item).filter((value) => value !== '');
  criteria.serviceBilledTos = criteria.serviceBilledTos.map(item => item.trim()).map(item => item === 'Empty' ? 'ISEMPTY' : item).filter((value) => value !== '');
  let paramString = '';
  if (criteria.tenantNames) {
    criteria.tenantNames.forEach((name) => {
      paramString += '&tenantNames=' + encodeURIComponent(name);
    });
  }
  if (criteria.masterCompanyNames) {
    criteria.masterCompanyNames.forEach((name) => {
      paramString += '&masterCompanyNames=' + encodeURIComponent(name);
    });
  }
  if (criteria.companyNames) {
    criteria.companyNames.forEach((name) => {
      paramString += '&companyNames=' + encodeURIComponent(name);
    });
  }
  if (criteria.serviceTypes) {
    criteria.serviceTypes.forEach((serviceType) => {
      paramString += '&serviceTypes=' + encodeURIComponent(serviceType);
    });
  }
  if (criteria.providers) {
    criteria.providers.forEach((provider) => {
      paramString += '&providers=' + encodeURIComponent(provider);
    });
  }
  if (criteria.serviceBilledTos) {
    criteria.serviceBilledTos.forEach((serviceBilledTo) => {
      paramString += '&serviceBilledTos=' + encodeURIComponent(serviceBilledTo);
    });
  }
  return paramString;
}
