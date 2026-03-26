import { environment } from '../config/environment';

interface RequestConfig {
  method: string;
  headers?: Record<string, string>;
  body?: unknown;
  responseType?: 'json' | 'blob' | 'text';
}

interface ApiResponse<T = unknown> {
  data: T;
  status: number;
  statusText: string;
  headers: Headers;
}

class ApiClient {
  private baseURL: string;

  constructor(baseURL: string) {
    this.baseURL = baseURL;
  }

  private async getAuthToken(): Promise<string | null> {
    // MSAL token acquisition disabled for local development
    // In production, this would acquire tokens via MSAL
    return null;
  }

  private async request<T = unknown>(
    url: string,
    config: RequestConfig
  ): Promise<ApiResponse<T>> {
    const token = await this.getAuthToken();

    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      ...config.headers,
    };

    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const fetchConfig: RequestInit = {
      method: config.method,
      headers,
    };

    if (config.body) {
      fetchConfig.body = JSON.stringify(config.body);
    }

    const fullUrl = url.startsWith('http') ? url : `${this.baseURL}${url}`;

    try {
      const response = await fetch(fullUrl, fetchConfig);

      // Handle unauthorized
      if (response.status === 401) {
        console.error('Unauthorized request');
      }

      // Parse response based on type
      let data: T;
      const responseType = config.responseType || 'json';

      if (responseType === 'blob') {
        data = (await response.blob()) as T;
      } else if (responseType === 'text') {
        data = (await response.text()) as T;
      } else {
        const text = await response.text();
        data = text ? JSON.parse(text) : null;
      }

      // Check if response is ok
      if (!response.ok) {
        const error = {
          response: {
            data,
            status: response.status,
            statusText: response.statusText,
            headers: response.headers,
          },
          message: `Request failed with status ${response.status}`,
        };
        throw error;
      }

      return {
        data,
        status: response.status,
        statusText: response.statusText,
        headers: response.headers,
      };
    } catch (error) {
      // Re-throw with consistent error structure
      if (error instanceof Error && 'response' in error) {
        throw error;
      }
      throw {
        message: error instanceof Error ? error.message : 'Network request failed',
        response: {
          status: 0,
          statusText: 'Network Error',
          data: null,
        },
      };
    }
  }

  async get<T = unknown>(url: string, config?: { headers?: Record<string, string> }): Promise<ApiResponse<T>> {
    return this.request<T>(url, {
      method: 'GET',
      headers: config?.headers,
    });
  }

  async post<T = unknown>(
    url: string,
    data?: unknown,
    config?: { headers?: Record<string, string>; responseType?: 'json' | 'blob' | 'text' }
  ): Promise<ApiResponse<T>> {
    return this.request<T>(url, {
      method: 'POST',
      body: data,
      headers: config?.headers,
      responseType: config?.responseType,
    });
  }

  async put<T = unknown>(
    url: string,
    data?: unknown,
    config?: { headers?: Record<string, string> }
  ): Promise<ApiResponse<T>> {
    return this.request<T>(url, {
      method: 'PUT',
      body: data,
      headers: config?.headers,
    });
  }

  async delete<T = unknown>(url: string, config?: { headers?: Record<string, string> }): Promise<ApiResponse<T>> {
    return this.request<T>(url, {
      method: 'DELETE',
      headers: config?.headers,
    });
  }

  async patch<T = unknown>(
    url: string,
    data?: unknown,
    config?: { headers?: Record<string, string> }
  ): Promise<ApiResponse<T>> {
    return this.request<T>(url, {
      method: 'PATCH',
      body: data,
      headers: config?.headers,
    });
  }
}

export const apiClient = new ApiClient(environment.appUrl);
export default apiClient;
