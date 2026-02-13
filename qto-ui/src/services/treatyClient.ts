/**
 * Eden Treaty - Type-Safe API Client (BREE)
 *
 * Connects to Elysia BFF with full type safety.
 * BFF runs on port 3000 and proxies to Spring Boot.
 */

import { treaty } from '@elysiajs/eden';
import type { App } from '../../server/index';

const BFF_URL = import.meta.env.VITE_BFF_URL || 'http://localhost:3000';

export const treatyClient = treaty<App>(BFF_URL);

export default treatyClient;
