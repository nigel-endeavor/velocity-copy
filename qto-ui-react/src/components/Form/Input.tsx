/**
 * Input Component
 * Reusable text input with label and error handling
 */

import React, { InputHTMLAttributes, forwardRef, useId } from 'react';

export interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
  helperText?: string;
  fullWidth?: boolean;
  startIcon?: React.ReactNode;
  endIcon?: React.ReactNode;
}

export const Input = forwardRef<HTMLInputElement, InputProps>(
  (
    {
      label,
      error,
      helperText,
      fullWidth = false,
      startIcon,
      endIcon,
      className = '',
      id,
      ...props
    },
    ref
  ) => {
    const generatedId = useId();
    const inputId = id || `input-${generatedId}`;
    const hasError = Boolean(error);

    return (
      <div className={fullWidth ? 'w-full' : ''}>
        {label && (
          <label
            htmlFor={inputId}
            className="block text-sm font-medium text-gray-700 mb-1"
          >
            {label}
            {props.required && <span className="text-red-500 ml-1">*</span>}
          </label>
        )}
        <div className="relative">
          {startIcon && (
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              {startIcon}
            </div>
          )}
          <input
            ref={ref}
            id={inputId}
            className={`
              block w-full px-4 py-2 border rounded-lg shadow-sm
              focus:ring-2 focus:outline-none transition-colors
              ${startIcon ? 'pl-10' : ''}
              ${endIcon ? 'pr-10' : ''}
              ${
                hasError
                  ? 'border-red-300 focus:ring-red-500 focus:border-red-500'
                  : 'border-gray-300 focus:ring-primary-500 focus:border-primary-500'
              }
              ${props.disabled ? 'bg-gray-100 cursor-not-allowed' : 'bg-white'}
              ${className}
            `}
            {...props}
          />
          {endIcon && (
            <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
              {endIcon}
            </div>
          )}
        </div>
        {(error || helperText) && (
          <p
            className={`mt-1 text-sm ${
              hasError ? 'text-red-600' : 'text-gray-500'
            }`}
          >
            {error || helperText}
          </p>
        )}
      </div>
    );
  }
);

Input.displayName = 'Input';
