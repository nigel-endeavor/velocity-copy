/**
 * Checkbox Component
 * Reusable checkbox with label
 */

import React, { InputHTMLAttributes, forwardRef, useId } from 'react';

export interface CheckboxProps extends Omit<InputHTMLAttributes<HTMLInputElement>, 'type'> {
  label?: string;
  helperText?: string;
  error?: string;
}

export const Checkbox = forwardRef<HTMLInputElement, CheckboxProps>(
  ({ label, helperText, error, className = '', id, ...props }, ref) => {
    const generatedId = useId();
    const checkboxId = id || `checkbox-${generatedId}`;
    const hasError = Boolean(error);

    return (
      <div className="flex items-start">
        <div className="flex items-center h-5">
          <input
            ref={ref}
            id={checkboxId}
            type="checkbox"
            className={`
              h-4 w-4 rounded border-gray-300
              text-primary focus:ring-primary
              ${props.disabled ? 'cursor-not-allowed opacity-50' : 'cursor-pointer'}
              ${className}
            `}
            {...props}
          />
        </div>
        {(label || helperText || error) && (
          <div className="ml-3 text-sm">
            {label && (
              <label
                htmlFor={checkboxId}
                className={`font-medium ${
                  props.disabled ? 'text-gray-400' : 'text-gray-700'
                } ${props.disabled ? '' : 'cursor-pointer'}`}
              >
                {label}
              </label>
            )}
            {(helperText || error) && (
              <p className={hasError ? 'text-red-600' : 'text-gray-500'}>
                {error || helperText}
              </p>
            )}
          </div>
        )}
      </div>
    );
  }
);

Checkbox.displayName = 'Checkbox';
