
import * as reducer from './table-deprecated.reducer';
import {
  getSelectedItems,
  getIsLoading,
  getSelectedIs,
  getProvisioners,
  getClientManagers,
  getServiceJeopardy,
  getJeopResponsibility,
  getCarriers,
  getSpeed,
  getProtocols,
  getMediaTypes,
  getMultieditConfig,
  selectTableDeprecatedState,
  getCantUpdate
} from './table-deprecated.selectors';
import { tableDeprecatedStoreFeatureKey } from './table-deprecated.reducer';

describe('TableDeprecated Selectors', () => {
  let state: any;

  const mockTableDeprecatedState: reducer.TableDeprecatedState = {
    cantUpdate: [],
    selectedItems: [
      { id: 1 },
      { id: 2 },
      { id: 3 },
    ],
    provisioners: [
      {
        id: 1,
        username: 'john_doe',
        displayName: 'John Doe',
        emailAddress: 'john.doe@example.com',
        lastLoginTime: new Date('2023-05-16T09:30:00'),
        version: 0
      },
      // Add more provisioner objects here
    ],
    clientManagers: [
      {
        id: 1,
        display: 'Client Manager 1',
        value: 'client_manager_1',
        active: true,
        sortSequence: 1,
        parentId: 1,
        version: 0
      },
      // Add more client manager objects here
    ],
    serviceJeopardy: [
      {
        id: 1,
        display: 'Service Jeopardy 1',
        value: 'service_jeopardy_1',
        active: true,
        sortSequence: 1,
        parentId: 1,
        version: 0
      },
      // Add more service jeopardy objects here
    ],
    jeopardyResponsibility: [
      {
        id: 1,
        display: 'Jeopardy Responsibility 1',
        value: 'jeopardy_responsibility_1',
        active: true,
        sortSequence: 1,
        parentId: 1,
        version: 0
      },
      // Add more jeopardy responsibility objects here
    ],
    carriers: [
      {
        id: 1,
        display: 'Carrier 1',
        value: 'carrier_1',
        active: true,
        sortSequence: 1,
        parentId: 1,
        version: 0
      },
      // Add more carrier objects here
    ],
    speed: [
      {
        id: 1,
        display: 'Speed 1',
        value: 'speed_1',
        active: true,
        sortSequence: 1,
        parentId: 1,
        version: 0
      },
      // Add more speed objects here
    ],
    protocols: [
      {
        id: 1,
        display: 'Protocol 1',
        value: 'protocol_1',
        active: true,
        sortSequence: 1,
        parentId: 1,
        version: 0
      },
      // Add more protocol objects here
    ],
    mediaTypes: [
      {
        id: 1,
        display: 'Media Type 1',
        value: 'media_type_1',
        active: true,
        sortSequence: 1,
        parentId: 1,
        version: 0
      },
      // Add more media type objects here
    ],
    isLoading: false,
  };

  const buildStore = () => ({
    [tableDeprecatedStoreFeatureKey]: {
      ...mockTableDeprecatedState
    }
  });

  beforeEach(() => {

    state = buildStore();
  });

  test(`should return the current value for TableDeprecatedState`, () => {
    expect(selectTableDeprecatedState(state)).toEqual(mockTableDeprecatedState);
  });

  test(`should return the getSelectedItems for TableDeprecatedState`, () => {
    expect(getSelectedItems(state)).toEqual(mockTableDeprecatedState.selectedItems);
  });

  test(`should return the isLoading for TableDeprecatedState`, () => {
    expect(getIsLoading(state)).toEqual(mockTableDeprecatedState.isLoading);
  });

  test(`should return the cantUpdate for TableDeprecatedState`, () => {
    expect(getCantUpdate(state)).toEqual(mockTableDeprecatedState.cantUpdate);
  });

  test(`should return the cantUpdate for TableDeprecatedState`, () => {
    expect(getCantUpdate(state)).toEqual(mockTableDeprecatedState.cantUpdate);
  });

  test(`should return the selected items Ids for TableDeprecatedState`, () => {
    expect(getSelectedIs(state)).toEqual([1, 2, 3]);
  });

  test(`should return the provisioners for TableDeprecatedState`, () => {
    expect(getProvisioners(state)).toEqual(mockTableDeprecatedState.provisioners);
  });

  test(`should return the client managers for TableDeprecatedState`, () => {
    expect(getClientManagers(state)).toEqual(mockTableDeprecatedState.clientManagers);
  });

  test(`should return the service jeopardy for TableDeprecatedState`, () => {
    expect(getServiceJeopardy(state)).toEqual(mockTableDeprecatedState.serviceJeopardy);
  });

  test(`should return the jeop responsibility for TableDeprecatedState`, () => {
    expect(getJeopResponsibility(state)).toEqual(mockTableDeprecatedState.jeopardyResponsibility);
  });

  test(`should return the carriers for TableDeprecatedState`, () => {
    expect(getCarriers(state)).toEqual(mockTableDeprecatedState.carriers);
  });

  test(`should return the speed for TableDeprecatedState`, () => {
    expect(getSpeed(state)).toEqual(mockTableDeprecatedState.speed);
  });

  test(`should return the protocols for TableDeprecatedState`, () => {
    expect(getProtocols(state)).toEqual(mockTableDeprecatedState.protocols);
  });

  test(`should return the media types for TableDeprecatedState`, () => {
    expect(getMediaTypes(state)).toEqual(mockTableDeprecatedState.mediaTypes);
  });

  test(`should return the config for TableDeprecatedState`, () => {
    expect(getMultieditConfig(state)).toEqual({
      provisioner: mockTableDeprecatedState.provisioners,
      vertekProjectManager: mockTableDeprecatedState.provisioners,
      clientProjectManager: mockTableDeprecatedState.clientManagers,
      jeopDescription: mockTableDeprecatedState.serviceJeopardy,
      jeopResponsibility: mockTableDeprecatedState.jeopardyResponsibility,
      jeopAssignedTo: mockTableDeprecatedState.provisioners,
      carrier: mockTableDeprecatedState.carriers,
      networkProtocol: mockTableDeprecatedState.protocols,
      uploadSpeed: mockTableDeprecatedState.speed,
      downloadSpeed: mockTableDeprecatedState.speed,
      mediaType: mockTableDeprecatedState.mediaTypes
    });
  });
});
