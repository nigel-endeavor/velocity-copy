/**
 * New Order Wizard
 *
 * Multi-step form for creating a new order.
 * Mirrors the Angular new-order-wizard with steps:
 *   1. Order Details (customer, client order ID)
 *   2. Contacts & Billing (sales, tech, auth contacts + billing address)
 *   3. Locations (address, LCON)
 *   4. Services (per location)
 *   5. Review & Submit
 */

import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCreateOrderMutation } from '@/services/api/ordersApi';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { ArrowLeft, ArrowRight, Check, Plus, Trash2, Loader2 } from 'lucide-react';
import { cn } from '@/lib/utils';

/* ------------------------------------------------------------------ */
/*  Types                                                             */
/* ------------------------------------------------------------------ */

interface OrderCreateAddress {
  address1: string;
  address2: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
}

interface OrderCreateContact {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
}

interface OrderCreateServiceData {
  serviceType: string;
  provider: string;
  downloadSpeed: string;
  uploadSpeed: string;
  mediaType: string;
  mrc: string;
  nrc: string;
  contractTerm: string;
  description: string;
}

interface OrderCreateLocationData {
  clientLocationId: string;
  locationType: string;
  locationInfo: string;
  lconName: string;
  lconEmail: string;
  lconPhone: string;
  address: OrderCreateAddress;
  services: OrderCreateServiceData[];
}

interface WizardFormData {
  // Step 1: Order Details
  masterCustomerId: string;
  endCustomerId: string;
  clientOrderId: string;
  // Step 2: Contacts
  salesContact: OrderCreateContact;
  techContact: OrderCreateContact;
  authContact: OrderCreateContact;
  billingAddress: OrderCreateAddress;
  // Step 3 & 4: Locations with Services
  locations: OrderCreateLocationData[];
}

const STEPS = [
  { label: 'Order Details', description: 'Customer and order info' },
  { label: 'Contacts', description: 'Sales, tech, and billing contacts' },
  { label: 'Locations', description: 'Add location addresses' },
  { label: 'Services', description: 'Add services per location' },
  { label: 'Review', description: 'Review and submit' },
];

const SERVICE_TYPES = [
  'DIA', 'Broadband', 'Ethernet', 'MPLS', 'UCaaS',
  'Cross Connect', 'Television', 'Microsoft Licenses',
  'Engineering - Endpoint', 'Engineering - IAM',
];

const LOCATION_TYPES = ['On-Net', 'Off-Net', 'Near-Net', 'Data Center', 'Remote'];

const EMPTY_ADDRESS: OrderCreateAddress = {
  address1: '', address2: '', city: '', state: '', postalCode: '', country: 'US',
};

const EMPTY_CONTACT: OrderCreateContact = {
  firstName: '', lastName: '', email: '', phone: '',
};

const EMPTY_SERVICE: OrderCreateServiceData = {
  serviceType: '', provider: '', downloadSpeed: '', uploadSpeed: '',
  mediaType: '', mrc: '', nrc: '', contractTerm: '', description: '',
};

const EMPTY_LOCATION: OrderCreateLocationData = {
  clientLocationId: '', locationType: '', locationInfo: '',
  lconName: '', lconEmail: '', lconPhone: '',
  address: { ...EMPTY_ADDRESS },
  services: [{ ...EMPTY_SERVICE }],
};

const INITIAL_FORM: WizardFormData = {
  masterCustomerId: '',
  endCustomerId: '',
  clientOrderId: '',
  salesContact: { ...EMPTY_CONTACT },
  techContact: { ...EMPTY_CONTACT },
  authContact: { ...EMPTY_CONTACT },
  billingAddress: { ...EMPTY_ADDRESS },
  locations: [{ ...EMPTY_LOCATION, services: [{ ...EMPTY_SERVICE }] }],
};

/* ------------------------------------------------------------------ */
/*  Wizard Component                                                   */
/* ------------------------------------------------------------------ */

export default function NewOrderWizard() {
  const navigate = useNavigate();
  const [createOrder, { isLoading }] = useCreateOrderMutation();
  const [step, setStep] = useState(0);
  const [form, setForm] = useState<WizardFormData>({ ...INITIAL_FORM });
  const [error, setError] = useState<string | null>(null);

  /* ---- Field Helpers ---- */
  const updateField = (field: keyof WizardFormData, value: any) =>
    setForm((prev) => ({ ...prev, [field]: value }));

  const updateContact = (
    contactField: 'salesContact' | 'techContact' | 'authContact',
    field: keyof OrderCreateContact,
    value: string,
  ) =>
    setForm((prev) => ({
      ...prev,
      [contactField]: { ...prev[contactField], [field]: value },
    }));

  const updateBillingAddress = (field: keyof OrderCreateAddress, value: string) =>
    setForm((prev) => ({
      ...prev,
      billingAddress: { ...prev.billingAddress, [field]: value },
    }));

  const updateLocation = (index: number, field: keyof OrderCreateLocationData, value: any) =>
    setForm((prev) => {
      const locs = [...prev.locations];
      locs[index] = { ...locs[index], [field]: value };
      return { ...prev, locations: locs };
    });

  const updateLocationAddress = (locIdx: number, field: keyof OrderCreateAddress, value: string) =>
    setForm((prev) => {
      const locs = [...prev.locations];
      locs[locIdx] = { ...locs[locIdx], address: { ...locs[locIdx].address, [field]: value } };
      return { ...prev, locations: locs };
    });

  const addLocation = () =>
    setForm((prev) => ({
      ...prev,
      locations: [...prev.locations, { ...EMPTY_LOCATION, services: [{ ...EMPTY_SERVICE }] }],
    }));

  const removeLocation = (index: number) =>
    setForm((prev) => ({
      ...prev,
      locations: prev.locations.filter((_, i) => i !== index),
    }));

  const updateService = (
    locIdx: number, svcIdx: number, field: keyof OrderCreateServiceData, value: string,
  ) =>
    setForm((prev) => {
      const locs = [...prev.locations];
      const svcs = [...locs[locIdx].services];
      svcs[svcIdx] = { ...svcs[svcIdx], [field]: value };
      locs[locIdx] = { ...locs[locIdx], services: svcs };
      return { ...prev, locations: locs };
    });

  const addService = (locIdx: number) =>
    setForm((prev) => {
      const locs = [...prev.locations];
      locs[locIdx] = { ...locs[locIdx], services: [...locs[locIdx].services, { ...EMPTY_SERVICE }] };
      return { ...prev, locations: locs };
    });

  const removeService = (locIdx: number, svcIdx: number) =>
    setForm((prev) => {
      const locs = [...prev.locations];
      locs[locIdx] = {
        ...locs[locIdx],
        services: locs[locIdx].services.filter((_, i) => i !== svcIdx),
      };
      return { ...prev, locations: locs };
    });

  /* ---- Submit ---- */
  const handleSubmit = async () => {
    setError(null);
    const dto = {
      dtoList: [
        {
          masterCustomer: form.masterCustomerId
            ? { id: Number(form.masterCustomerId) }
            : undefined,
          endCustomer: form.endCustomerId
            ? { id: Number(form.endCustomerId) }
            : undefined,
          clientOrderId: form.clientOrderId,
          salesContact: form.salesContact,
          techContact: form.techContact,
          authContact: form.authContact,
          billingAddress: form.billingAddress,
          locations: form.locations.map((loc) => ({
            clientLocationId: loc.clientLocationId,
            locationType: loc.locationType,
            locationInfo: loc.locationInfo,
            lconName: loc.lconName,
            lconEmail: loc.lconEmail,
            lconPhone: loc.lconPhone,
            address: loc.address,
            services: loc.services.map((svc) => ({
              serviceType: svc.serviceType,
              provider: svc.provider,
              downloadSpeed: svc.downloadSpeed,
              uploadSpeed: svc.uploadSpeed,
              mediaType: svc.mediaType,
              mrc: svc.mrc ? parseFloat(svc.mrc) : null,
              nrc: svc.nrc ? parseFloat(svc.nrc) : null,
              contractTerm: svc.contractTerm,
              description: svc.description,
            })),
          })),
        },
      ],
    };
    try {
      const result = await createOrder(dto).unwrap();
      // Navigate to the newly created order
      const firstDto = (result as any)?.dtoList?.[0];
      if (firstDto?.orderId) {
        navigate(`/orders/${firstDto.orderId}`);
      } else {
        navigate('/orders');
      }
    } catch (e: any) {
      setError(e?.data?.message || e?.message || 'Failed to create order');
    }
  };

  /* ---- Navigation ---- */
  const canGoNext = () => {
    if (step === 0) return !!form.clientOrderId;
    if (step === 2) return form.locations.length > 0;
    return true;
  };

  /* ---- Render Helpers ---- */
  const renderInput = (
    label: string,
    value: string,
    onChange: (v: string) => void,
    opts?: { placeholder?: string; required?: boolean; type?: string },
  ) => (
    <div className="space-y-1">
      <Label>{label}{opts?.required && <span className="text-red-500 ml-0.5">*</span>}</Label>
      <Input
        type={opts?.type || 'text'}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        placeholder={opts?.placeholder}
      />
    </div>
  );

  const renderAddressFields = (
    addr: OrderCreateAddress,
    onChange: (field: keyof OrderCreateAddress, val: string) => void,
  ) => (
    <div className="grid grid-cols-6 gap-3">
      <div className="col-span-4">{renderInput('Address 1', addr.address1, (v) => onChange('address1', v))}</div>
      <div className="col-span-2">{renderInput('Address 2', addr.address2, (v) => onChange('address2', v))}</div>
      <div className="col-span-2">{renderInput('City', addr.city, (v) => onChange('city', v))}</div>
      <div className="col-span-1">{renderInput('State', addr.state, (v) => onChange('state', v))}</div>
      <div className="col-span-1">{renderInput('Postal Code', addr.postalCode, (v) => onChange('postalCode', v))}</div>
      <div className="col-span-2">{renderInput('Country', addr.country, (v) => onChange('country', v))}</div>
    </div>
  );

  const renderContactFields = (
    label: string,
    contact: OrderCreateContact,
    onChange: (field: keyof OrderCreateContact, val: string) => void,
  ) => (
    <div className="space-y-3">
      <h4 className="font-medium text-sm">{label}</h4>
      <div className="grid grid-cols-4 gap-3">
        {renderInput('First Name', contact.firstName, (v) => onChange('firstName', v))}
        {renderInput('Last Name', contact.lastName, (v) => onChange('lastName', v))}
        {renderInput('Email', contact.email, (v) => onChange('email', v), { type: 'email' })}
        {renderInput('Phone', contact.phone, (v) => onChange('phone', v), { type: 'tel' })}
      </div>
    </div>
  );

  /* ================================================================ */
  /*  STEP RENDERERS                                                  */
  /* ================================================================ */

  const renderStep0 = () => (
    <div className="space-y-4">
      <h3 className="text-lg font-semibold">Order Details</h3>
      <div className="grid grid-cols-2 gap-4">
        {renderInput('Client Order ID', form.clientOrderId, (v) => updateField('clientOrderId', v), { required: true, placeholder: 'e.g. ORD-2026-001' })}
        {renderInput('Master Customer ID', form.masterCustomerId, (v) => updateField('masterCustomerId', v), { placeholder: 'Company ID (e.g. 1)' })}
        {renderInput('End Customer ID', form.endCustomerId, (v) => updateField('endCustomerId', v), { placeholder: 'Company ID (optional)' })}
      </div>
    </div>
  );

  const renderStep1 = () => (
    <div className="space-y-6">
      <h3 className="text-lg font-semibold">Contacts & Billing</h3>
      {renderContactFields('Sales Contact', form.salesContact, (f, v) => updateContact('salesContact', f, v))}
      {renderContactFields('Technical Contact', form.techContact, (f, v) => updateContact('techContact', f, v))}
      {renderContactFields('Authorization Contact', form.authContact, (f, v) => updateContact('authContact', f, v))}
      <div className="space-y-3">
        <h4 className="font-medium text-sm">Billing Address</h4>
        {renderAddressFields(form.billingAddress, updateBillingAddress)}
      </div>
    </div>
  );

  const renderStep2 = () => (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <h3 className="text-lg font-semibold">Locations</h3>
        <Button variant="outline" size="sm" onClick={addLocation}>
          <Plus className="h-4 w-4 mr-1" /> Add Location
        </Button>
      </div>
      {form.locations.map((loc, locIdx) => (
        <Card key={locIdx} className="relative">
          <CardHeader className="pb-3 flex flex-row items-center justify-between">
            <CardTitle className="text-sm">Location {locIdx + 1}</CardTitle>
            {form.locations.length > 1 && (
              <Button variant="ghost" size="icon" className="h-7 w-7" onClick={() => removeLocation(locIdx)}>
                <Trash2 className="h-4 w-4 text-red-500" />
              </Button>
            )}
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="grid grid-cols-3 gap-3">
              {renderInput('Client Location ID', loc.clientLocationId, (v) => updateLocation(locIdx, 'clientLocationId', v))}
              <div className="space-y-1">
                <Label>Location Type</Label>
                <Select value={loc.locationType} onValueChange={(v) => updateLocation(locIdx, 'locationType', v)}>
                  <SelectTrigger><SelectValue placeholder="Select type" /></SelectTrigger>
                  <SelectContent>
                    {LOCATION_TYPES.map((t) => <SelectItem key={t} value={t}>{t}</SelectItem>)}
                  </SelectContent>
                </Select>
              </div>
              {renderInput('Location Info', loc.locationInfo, (v) => updateLocation(locIdx, 'locationInfo', v))}
            </div>
            <h5 className="font-medium text-xs text-muted-foreground mt-2">Address</h5>
            {renderAddressFields(loc.address, (f, v) => updateLocationAddress(locIdx, f, v))}
            <h5 className="font-medium text-xs text-muted-foreground mt-2">LCON (Local Contact)</h5>
            <div className="grid grid-cols-3 gap-3">
              {renderInput('Name', loc.lconName, (v) => updateLocation(locIdx, 'lconName', v))}
              {renderInput('Email', loc.lconEmail, (v) => updateLocation(locIdx, 'lconEmail', v))}
              {renderInput('Phone', loc.lconPhone, (v) => updateLocation(locIdx, 'lconPhone', v))}
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  );

  const renderStep3 = () => (
    <div className="space-y-4">
      <h3 className="text-lg font-semibold">Services per Location</h3>
      {form.locations.map((loc, locIdx) => (
        <Card key={locIdx}>
          <CardHeader className="pb-3 flex flex-row items-center justify-between">
            <CardTitle className="text-sm">
              Location {locIdx + 1}: {loc.clientLocationId || loc.address.address1 || '(no address)'}
            </CardTitle>
            <Button variant="outline" size="sm" onClick={() => addService(locIdx)}>
              <Plus className="h-3 w-3 mr-1" /> Service
            </Button>
          </CardHeader>
          <CardContent className="space-y-4">
            {loc.services.map((svc, svcIdx) => (
              <div key={svcIdx} className="border rounded-lg p-3 space-y-3 relative">
                <div className="flex items-center justify-between">
                  <span className="text-xs font-medium text-muted-foreground">Service {svcIdx + 1}</span>
                  {loc.services.length > 1 && (
                    <Button variant="ghost" size="icon" className="h-6 w-6" onClick={() => removeService(locIdx, svcIdx)}>
                      <Trash2 className="h-3 w-3 text-red-500" />
                    </Button>
                  )}
                </div>
                <div className="grid grid-cols-4 gap-3">
                  <div className="space-y-1">
                    <Label>Service Type</Label>
                    <Select value={svc.serviceType} onValueChange={(v) => updateService(locIdx, svcIdx, 'serviceType', v)}>
                      <SelectTrigger><SelectValue placeholder="Select" /></SelectTrigger>
                      <SelectContent>
                        {SERVICE_TYPES.map((t) => <SelectItem key={t} value={t}>{t}</SelectItem>)}
                      </SelectContent>
                    </Select>
                  </div>
                  {renderInput('Provider', svc.provider, (v) => updateService(locIdx, svcIdx, 'provider', v))}
                  {renderInput('Download Speed', svc.downloadSpeed, (v) => updateService(locIdx, svcIdx, 'downloadSpeed', v))}
                  {renderInput('Upload Speed', svc.uploadSpeed, (v) => updateService(locIdx, svcIdx, 'uploadSpeed', v))}
                </div>
                <div className="grid grid-cols-4 gap-3">
                  {renderInput('Media Type', svc.mediaType, (v) => updateService(locIdx, svcIdx, 'mediaType', v))}
                  {renderInput('MRC ($)', svc.mrc, (v) => updateService(locIdx, svcIdx, 'mrc', v), { type: 'number' })}
                  {renderInput('NRC ($)', svc.nrc, (v) => updateService(locIdx, svcIdx, 'nrc', v), { type: 'number' })}
                  {renderInput('Contract Term', svc.contractTerm, (v) => updateService(locIdx, svcIdx, 'contractTerm', v), { placeholder: 'e.g. 36 months' })}
                </div>
              </div>
            ))}
          </CardContent>
        </Card>
      ))}
    </div>
  );

  const renderStep4 = () => (
    <div className="space-y-4">
      <h3 className="text-lg font-semibold">Review & Submit</h3>

      {error && (
        <div className="p-3 text-sm text-red-800 bg-red-50 rounded-lg border border-red-200">
          {error}
        </div>
      )}

      <Card>
        <CardHeader><CardTitle className="text-sm">Order Details</CardTitle></CardHeader>
        <CardContent>
          <dl className="grid grid-cols-3 gap-2 text-sm">
            <div><dt className="text-muted-foreground">Client Order ID</dt><dd className="font-medium">{form.clientOrderId || '—'}</dd></div>
            <div><dt className="text-muted-foreground">Master Customer ID</dt><dd className="font-medium">{form.masterCustomerId || '—'}</dd></div>
            <div><dt className="text-muted-foreground">End Customer ID</dt><dd className="font-medium">{form.endCustomerId || '—'}</dd></div>
          </dl>
        </CardContent>
      </Card>

      <Card>
        <CardHeader><CardTitle className="text-sm">Contacts</CardTitle></CardHeader>
        <CardContent className="space-y-2 text-sm">
          {[
            { label: 'Sales', c: form.salesContact },
            { label: 'Technical', c: form.techContact },
            { label: 'Authorization', c: form.authContact },
          ].map(({ label, c }) => (
            <div key={label}>
              <span className="text-muted-foreground">{label}:</span>{' '}
              {c.firstName || c.lastName ? `${c.firstName} ${c.lastName}`.trim() : '—'}
              {c.email && ` (${c.email})`}
            </div>
          ))}
        </CardContent>
      </Card>

      <Card>
        <CardHeader><CardTitle className="text-sm">Locations ({form.locations.length})</CardTitle></CardHeader>
        <CardContent className="space-y-3">
          {form.locations.map((loc, i) => (
            <div key={i} className="border-l-2 pl-3 text-sm space-y-1">
              <div className="font-medium">{loc.clientLocationId || `Location ${i + 1}`}</div>
              <div className="text-muted-foreground">
                {[loc.address.address1, loc.address.city, loc.address.state, loc.address.postalCode]
                  .filter(Boolean).join(', ') || 'No address'}
              </div>
              <div className="text-xs text-muted-foreground">
                {loc.services.length} service{loc.services.length !== 1 ? 's' : ''}
                {loc.services.map((s) => s.serviceType).filter(Boolean).length > 0 &&
                  `: ${loc.services.map((s) => s.serviceType).filter(Boolean).join(', ')}`}
              </div>
            </div>
          ))}
        </CardContent>
      </Card>
    </div>
  );

  const stepRenderers = [renderStep0, renderStep1, renderStep2, renderStep3, renderStep4];

  /* ================================================================ */
  /*  MAIN RENDER                                                      */
  /* ================================================================ */

  return (
    <div className="space-y-6 max-w-5xl mx-auto">
      {/* Header */}
      <div className="flex items-center gap-4">
        <Button variant="ghost" size="icon" onClick={() => navigate('/orders')}>
          <ArrowLeft className="h-5 w-5" />
        </Button>
        <div>
          <h1 className="text-2xl font-bold tracking-tight">New Order</h1>
          <p className="text-muted-foreground text-sm">
            Step {step + 1} of {STEPS.length}: {STEPS[step].description}
          </p>
        </div>
      </div>

      {/* Step Indicator */}
      <nav className="flex items-center gap-2">
        {STEPS.map((s, i) => (
          <div key={i} className="flex items-center gap-2">
            <button
              className={cn(
                'flex items-center gap-2 text-xs font-medium px-3 py-1.5 rounded-full transition-colors',
                i === step && 'bg-primary text-primary-foreground',
                i < step && 'bg-green-100 text-green-800',
                i > step && 'bg-muted text-muted-foreground',
              )}
              onClick={() => i <= step && setStep(i)}
              disabled={i > step}
            >
              {i < step ? <Check className="h-3 w-3" /> : <span>{i + 1}</span>}
              <span className="hidden sm:inline">{s.label}</span>
            </button>
            {i < STEPS.length - 1 && <div className="w-6 h-px bg-border" />}
          </div>
        ))}
      </nav>

      {/* Step Content */}
      <Card>
        <CardContent className="pt-6">
          {stepRenderers[step]()}
        </CardContent>
      </Card>

      {/* Footer Navigation */}
      <div className="flex items-center justify-between">
        <Button
          variant="outline"
          onClick={() => step === 0 ? navigate('/orders') : setStep(step - 1)}
        >
          <ArrowLeft className="h-4 w-4 mr-2" />
          {step === 0 ? 'Cancel' : 'Previous'}
        </Button>

        {step < STEPS.length - 1 ? (
          <Button onClick={() => setStep(step + 1)} disabled={!canGoNext()}>
            Next
            <ArrowRight className="h-4 w-4 ml-2" />
          </Button>
        ) : (
          <Button onClick={handleSubmit} disabled={isLoading}>
            {isLoading ? (
              <><Loader2 className="h-4 w-4 mr-2 animate-spin" /> Creating...</>
            ) : (
              <><Check className="h-4 w-4 mr-2" /> Create Order</>
            )}
          </Button>
        )}
      </div>
    </div>
  );
}
