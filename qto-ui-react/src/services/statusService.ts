import apiClient from './apiClient';

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
    const response = await apiClient.get<StatusResponse>('/status');
    return response.data;
  },

  ping: async (): Promise<PingResponse> => {
    const response = await apiClient.get<PingResponse>('/status/ping');
    return response.data;
  },
};

export default statusService;
