module.exports = {
  preset: 'jest-preset-angular',
  verbose: false,
  clearMocks: true,
  globalSetup: 'jest-preset-angular/global-setup',
  transform: {
    '^.+\\.(ts|js|jsx|mjs|html)$': 'jest-preset-angular',
  },
  snapshotSerializers: [
    'jest-preset-angular/build/serializers/no-ng-attributes',
    'jest-preset-angular/build/serializers/ng-snapshot',
    'jest-preset-angular/build/serializers/html-comment',
  ],
  globals: {
    'ts-jest': {
      tsconfig: '<rootDir>/tsconfig.spec.json',
      stringifyContentPathRegex: '\\.(html|svg)$',
      isolatedModules: true,
    },
  },
  modulePathIgnorePatterns: ['environment.test.ts'],

  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  coveragePathIgnorePatterns: [
    '<rootDir>/build/',
    '<rootDir>/node_modules/',
    '<rootDir>/testing/',
    '.module.ts',
    '.interface.ts',
    '.enum.ts',
    '.helper.ts',
    '.data.ts',
    '.mock.ts',
    '.const.ts',
    '.actions.ts',
    '.serializer.ts',
    'index.ts'
  ]
};
