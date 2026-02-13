/**
 * Status Service - BREE (Eden Treaty)
 *
 * Type-safe status endpoints via Treaty client.
 */

import { treatyClient } from './treatyClient';

export interface StatusResponse {
  application: string;
  version: string;
  status: string;
  phase: string;
  message: string;
  timestamp: string;
}

export interface PingResponse {
  message: string;
  timestamp: string;
}

export const statusService = {
  getStatus: async (): Promise<StatusResponse> => {
    const { data, error } = await treatyClient.status.get();
    if (error) throw error;
    return data as StatusResponse;
  },

  ping: async (): Promise<PingResponse> => {
    const { data, error } = await treatyClient.status.ping.get();
    if (error) throw error;
    return data as PingResponse;
  },
};

export default statusService;
