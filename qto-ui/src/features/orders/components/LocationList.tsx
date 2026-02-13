/**
 * Location List Component
 *
 * Displays accordion list of locations with nested services
 */

import { MapPin, Plus } from 'lucide-react';
import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from '@/components/ui/accordion';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';

import { useAppDispatch, useAppSelector } from '@/store/hooks';
import {
  selectOrderLocations,
  selectExpandedLocations,
} from '@/store/slices/orderDetailsSelectors';
import {
  toggleLocationExpansion,
  expandAllLocations,
  collapseAllLocations,
} from '@/store/slices/orderDetailsSlice';
import { usePermissions } from '@/shared/hooks/usePermissions';
import { formatLocationAddress } from '@/shared/types/models/location.model';

export default function LocationList() {
  const dispatch = useAppDispatch();
  const { canWrite } = usePermissions();
  const canEdit = canWrite('order-write');

  const locations = useAppSelector(selectOrderLocations);
  const expandedLocations = useAppSelector(selectExpandedLocations);

  const handleToggleExpansion = (locationId: number) => {
    dispatch(toggleLocationExpansion({ locationId }));
  };

  const handleExpandAll = () => {
    dispatch(expandAllLocations());
  };

  const handleCollapseAll = () => {
    dispatch(collapseAllLocations());
  };

  if (locations.length === 0) {
    return (
      <div className="text-center py-8">
        <p className="text-base text-muted-foreground mb-4">
          No locations yet
        </p>
        {canEdit && (
          <Button>
            <Plus className="mr-2 h-4 w-4" />
            Add Location
          </Button>
        )}
      </div>
    );
  }

  return (
    <div>
      {/* Controls */}
      <div className="flex justify-end gap-2 mb-4">
        <Button variant="outline" size="sm" onClick={handleExpandAll}>
          Expand All
        </Button>
        <Button variant="outline" size="sm" onClick={handleCollapseAll}>
          Collapse All
        </Button>
        {canEdit && (
          <Button size="sm">
            <Plus className="mr-2 h-4 w-4" />
            Add Location
          </Button>
        )}
      </div>

      {/* Location Accordions */}
      <Accordion type="multiple" value={expandedLocations.map(String)}>
        {locations.map((location) => {
          const serviceCount = location.services?.filter(
            (s) => s.status !== 'Service Cancelled'
          ).length || 0;

          return (
            <AccordionItem
              key={location.id || `temp-${Math.random()}`}
              value={String(location.id)}
            >
              <AccordionTrigger onClick={() => location.id && handleToggleExpansion(location.id)}>
                <div className="flex items-center gap-4 w-full">
                  <MapPin className="h-5 w-5 text-muted-foreground" />
                  <div className="flex-1 text-left">
                    <p className="font-medium">
                      {location.clientLocationId || 'New Location'}
                    </p>
                    <p className="text-sm text-muted-foreground">
                      {formatLocationAddress(location) || 'No address'}
                    </p>
                  </div>
                  <div className="flex gap-2">
                    <Badge variant={location.status === 'Location Cancelled' ? 'destructive' : 'default'}>
                      {location.status || 'Unknown'}
                    </Badge>
                    <Badge variant="outline">
                      {serviceCount} service{serviceCount !== 1 ? 's' : ''}
                    </Badge>
                  </div>
                </div>
              </AccordionTrigger>

              <AccordionContent>
                <div>
                  <p className="text-sm text-muted-foreground">
                    Location details and services will be displayed here.
                  </p>
                  <p className="text-sm text-muted-foreground mt-2">
                    MRC: ${location.mrc?.toFixed(2) || '0.00'} | NRC: ${location.nrc?.toFixed(2) || '0.00'}
                  </p>

                  {/* Service List Placeholder */}
                  {location.services && location.services.length > 0 && (
                    <div className="mt-4 pl-4 border-l-2 border-border">
                      <p className="text-sm font-medium mb-2">
                        Services ({serviceCount})
                      </p>
                      {location.services
                        .filter((s) => s.status !== 'Service Cancelled')
                        .map((service, index) => (
                          <div key={service.id || `service-${index}`} className="mb-2">
                            <p className="text-sm">
                              {service.type || 'Unknown Service'} - {service.provider || 'No provider'}
                            </p>
                            <p className="text-xs text-muted-foreground">
                              MRC: ${service.mrc?.toFixed(2) || '0.00'} | Status: {service.status || 'Unknown'}
                            </p>
                          </div>
                        ))}
                    </div>
                  )}
                </div>
              </AccordionContent>
            </AccordionItem>
          );
        })}
      </Accordion>
    </div>
  );
}
