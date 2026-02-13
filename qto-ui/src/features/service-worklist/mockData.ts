/**
 * Mock Service Data
 * Sample data for development and testing
 */

import type { Service } from './types';

// Generate mock services
export const generateMockServices = (count: number = 50): Service[] => {
  const statuses: Service['status'][] = ['PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'];
  const priorities: Service['priority'][] = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
  const serviceTypes = ['INTERNET', 'VOICE', 'DATA', 'CLOUD'];
  const customers = ['Acme Corp', 'TechCo Industries', 'Global Systems', 'Enterprise LLC', 'Innovation Inc'];
  const locations = ['New York HQ', 'San Francisco Office', 'Chicago Branch', 'Boston Center', 'Austin Campus'];
  const assignees = ['John Doe', 'Jane Smith', 'Bob Johnson', 'Alice Williams', 'Charlie Brown'];

  return Array.from({ length: count }, (_, i) => {
    const orderDate = new Date(2024, 0, 1 + Math.floor(Math.random() * 365));
    const dueDate = new Date(orderDate.getTime() + (30 + Math.random() * 60) * 24 * 60 * 60 * 1000);

    return {
      id: i + 1,
      serviceId: `SVC-${(10000 + i).toString()}`,
      customerName: customers[Math.floor(Math.random() * customers.length)],
      locationName: locations[Math.floor(Math.random() * locations.length)],
      serviceType: serviceTypes[Math.floor(Math.random() * serviceTypes.length)],
      status: statuses[Math.floor(Math.random() * statuses.length)],
      bandwidth: `${[10, 50, 100, 500, 1000][Math.floor(Math.random() * 5)]}Mbps`,
      orderDate: orderDate.toISOString(),
      dueDate: dueDate.toISOString(),
      assignedTo: assignees[Math.floor(Math.random() * assignees.length)],
      priority: priorities[Math.floor(Math.random() * priorities.length)],
      notes: 'Sample service order for testing',
    };
  });
};

export const mockServices = generateMockServices(50);
