import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root' // Ensures it's a singleton
})
export class ServiceTechnicalTooltipsService {
  readonly providerTooltip: string = "The organization which will be supplying the underlying service and Invoicing.";
  readonly accountNumberBanTooltip: string = "Account number the billed service will post or aggregate to.";
  readonly providerOrderNumberTooltip: string = "Individual unique order # related to the Provider.";
  readonly billCycleTooltip: string = "#1-31, Day of the month the billing cycle begins or posts.";
  readonly accountPasscodeTooltip: string = "If the provider assigns a unique ID to access or discuss services.";
  readonly microsoftLicensingTooltip: string = "Details about the Microsft licensing being assigned During the enablement phase";
  readonly numberOfUsersTooltip: string = "Enter the total number of users at time of contract the business will need supported.";
  readonly numberOfEndpointsTooltip: string = "Enter the number of endpoints or devices that the organization is supporting at the time of contract.";
  readonly otherTechNotesTooltip: string = "Other context or supporting narrative of the service delivery.";
}
