/**
 * Customer Details Page
 * Read-only detail view for a company with contacts and onboarding tasks
 */

import { useParams, useNavigate } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';

import { useGetCompanyQuery, useGetCompanyTasksQuery } from '@/services/api/companiesApi';
import { useGetContactsByCompanyQuery } from '@/services/api/contactsApi';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';

export default function CustomerDetails() {
  const { customerId } = useParams<{ customerId: string }>();
  const navigate = useNavigate();
  const id = Number(customerId);

  const { data: company, isLoading: companyLoading } = useGetCompanyQuery(id);
  const { data: contacts } = useGetContactsByCompanyQuery({ companyId: id });
  const { data: tasks } = useGetCompanyTasksQuery(id);

  if (companyLoading) {
    return (
      <div className="p-6">
        <div className="animate-pulse space-y-4">
          <div className="h-8 bg-gray-200 rounded w-1/3"></div>
          <div className="h-64 bg-gray-200 rounded"></div>
        </div>
      </div>
    );
  }

  if (!company) {
    return (
      <div className="p-6">
        <p className="text-muted-foreground">Customer not found.</p>
        <Button variant="outline" onClick={() => navigate('/customers')} className="mt-4">
          <ArrowLeft className="mr-2 h-4 w-4" />
          Back to Customers
        </Button>
      </div>
    );
  }

  const formatCurrency = (value: any) =>
    value != null ? `$${Number(value).toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}` : '$0.00';

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center gap-4">
        <Button variant="outline" size="sm" onClick={() => navigate('/customers')}>
          <ArrowLeft className="mr-2 h-4 w-4" />
          Back
        </Button>
        <h1 className="text-3xl font-bold">{company.name}</h1>
        <Badge variant={company.active ? 'default' : 'secondary'}>
          {company.active ? 'Active' : 'Inactive'}
        </Badge>
      </div>

      {/* Company Info */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <Card>
          <CardHeader><CardTitle>General</CardTitle></CardHeader>
          <CardContent className="space-y-2 text-sm">
            <div><span className="font-medium">Client ID:</span> {company.clientId || '-'}</div>
            <div><span className="font-medium">Type:</span> {company.type || '-'}</div>
            <div><span className="font-medium">UUID:</span> {company.uuid || '-'}</div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader><CardTitle>Address</CardTitle></CardHeader>
          <CardContent className="space-y-2 text-sm">
            <div>{company.address1 || '-'}</div>
            {company.address2 && <div>{company.address2}</div>}
            <div>{company.city}, {company.state} {company.postalCode}</div>
            {company.country && <div>{company.country}</div>}
          </CardContent>
        </Card>

        <Card>
          <CardHeader><CardTitle>Inventory</CardTitle></CardHeader>
          <CardContent className="space-y-2 text-sm">
            <div><span className="font-medium">Locations:</span> {company.inventoryLocationCount || 0}</div>
            <div><span className="font-medium">MRC:</span> {formatCurrency(company.inventoryMrc)}</div>
            <div><span className="font-medium">MRR:</span> {formatCurrency(company.inventoryMrr)}</div>
            <div><span className="font-medium">NRR:</span> {formatCurrency(company.inventoryNrr)}</div>
          </CardContent>
        </Card>
      </div>

      {/* Contacts */}
      {contacts && contacts.length > 0 && (
        <Card>
          <CardHeader><CardTitle>Contacts</CardTitle></CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Name</TableHead>
                  <TableHead>Type</TableHead>
                  <TableHead>Role</TableHead>
                  <TableHead>Email</TableHead>
                  <TableHead>Phone</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {contacts.map((contact) => (
                  <TableRow key={contact.id}>
                    <TableCell>{contact.firstName} {contact.lastName}</TableCell>
                    <TableCell>{contact.type}</TableCell>
                    <TableCell>{contact.role || '-'}</TableCell>
                    <TableCell>{contact.email || '-'}</TableCell>
                    <TableCell>{contact.phone || '-'}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      )}

      {/* Onboarding Tasks */}
      {tasks && tasks.length > 0 && (
        <Card>
          <CardHeader><CardTitle>Onboarding Tasks</CardTitle></CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Task</TableHead>
                  <TableHead>Assigned To</TableHead>
                  <TableHead>Completed</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {tasks.map((task) => (
                  <TableRow key={task.companyTaskId}>
                    <TableCell>{task.value}</TableCell>
                    <TableCell>{task.assignedTo || '-'}</TableCell>
                    <TableCell>
                      {task.completeDate ? (
                        <Badge variant="default">Complete</Badge>
                      ) : (
                        <Badge variant="secondary">Pending</Badge>
                      )}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      )}
    </div>
  );
}
