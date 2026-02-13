# Tailwind CSS Guide for QTO UI

## Overview

This project uses **Tailwind CSS v4** for styling. Tailwind is a utility-first CSS framework that allows you to build custom designs without leaving your HTML/JSX.

## Configuration

### Files
- `tailwind.config.js` - Tailwind configuration with custom colors
- `postcss.config.js` - PostCSS configuration for processing Tailwind
- `src/index.css` - Tailwind directives and global styles

### Custom Colors

We've extended Tailwind with a custom primary color palette matching the original Material-UI theme:

```js
primary: {
  50: '#e3f2fd',   // Lightest
  100: '#bbdefb',
  200: '#90caf9',
  300: '#64b5f6',
  400: '#42a5f5',
  500: '#1976d2',  // Base primary color
  600: '#1565c0',
  700: '#0d47a1',
  800: '#0a3d91',
  900: '#082e6f',  // Darkest
}
```

Usage: `bg-primary-600`, `text-primary-500`, `border-primary-400`

## Common Patterns

### Layout

```tsx
// Container with responsive padding
<div className="container mx-auto px-4 py-8">
  {/* Content */}
</div>

// Flexbox layouts
<div className="flex items-center justify-between">
  <div>Left content</div>
  <div>Right content</div>
</div>

// Grid layouts
<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
  <div>Column 1</div>
  <div>Column 2</div>
  <div>Column 3</div>
</div>
```

### Cards

```tsx
// Simple card
<div className="bg-white rounded-lg shadow-md p-6">
  <h2 className="text-2xl font-bold mb-4">Card Title</h2>
  <p className="text-gray-600">Card content</p>
</div>

// Card with hover effect
<div className="bg-white rounded-lg shadow-md hover:shadow-xl transition-shadow p-6">
  {/* Content */}
</div>
```

### Buttons

```tsx
// Primary button
<button className="px-6 py-3 bg-primary-600 text-white rounded-lg hover:bg-primary-700 transition-colors font-medium">
  Click Me
</button>

// Secondary button
<button className="px-6 py-3 bg-gray-200 text-gray-900 rounded-lg hover:bg-gray-300 transition-colors font-medium">
  Cancel
</button>

// Danger button
<button className="px-6 py-3 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors font-medium">
  Delete
</button>

// Ghost button
<button className="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-lg transition-colors">
  Ghost
</button>
```

### Forms

```tsx
// Input field
<input
  type="text"
  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
  placeholder="Enter text..."
/>

// Select dropdown
<select className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500">
  <option>Option 1</option>
  <option>Option 2</option>
</select>

// Checkbox
<input
  type="checkbox"
  className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
/>
```

### Typography

```tsx
// Headings
<h1 className="text-4xl font-bold text-gray-900">Page Title</h1>
<h2 className="text-3xl font-semibold text-gray-800">Section Title</h2>
<h3 className="text-2xl font-semibold text-gray-700">Subsection</h3>

// Paragraph
<p className="text-base text-gray-600 leading-relaxed">
  Regular paragraph text with good readability.
</p>

// Small text
<span className="text-sm text-gray-500">Small text or captions</span>
```

### Status Indicators

```tsx
// Success
<div className="bg-green-50 border border-green-200 rounded-lg p-4">
  <p className="text-green-800">Success message</p>
</div>

// Error
<div className="bg-red-50 border border-red-200 rounded-lg p-4">
  <p className="text-red-800">Error message</p>
</div>

// Warning
<div className="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
  <p className="text-yellow-800">Warning message</p>
</div>

// Info
<div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
  <p className="text-blue-800">Info message</p>
</div>
```

### Loading States

```tsx
// Spinner
<div className="flex items-center justify-center py-8">
  <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
</div>

// Skeleton loader
<div className="animate-pulse space-y-4">
  <div className="h-4 bg-gray-200 rounded w-3/4"></div>
  <div className="h-4 bg-gray-200 rounded w-1/2"></div>
</div>
```

## Responsive Design

Tailwind uses mobile-first breakpoints:

- `sm:` - 640px and up
- `md:` - 768px and up
- `lg:` - 1024px and up
- `xl:` - 1280px and up
- `2xl:` - 1536px and up

Example:
```tsx
<div className="text-sm md:text-base lg:text-lg xl:text-xl">
  Responsive text size
</div>

<div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
  {/* 1 column on mobile, 2 on tablet, 3 on desktop */}
</div>
```

## Reusable Components

Use the provided components in `src/components/`:

```tsx
import { Button } from '@/components/Button';
import { Card } from '@/components/Card';

// Button usage
<Button variant="primary" size="md" onClick={handleClick}>
  Click Me
</Button>

// Card usage
<Card padding="lg">
  <h2 className="text-2xl font-bold mb-4">Card Title</h2>
  <p>Card content</p>
</Card>
```

## Best Practices

1. **Use semantic HTML** - Use proper HTML elements (header, nav, main, section, etc.)
2. **Mobile-first** - Design for mobile, then add responsive classes for larger screens
3. **Consistent spacing** - Use Tailwind's spacing scale (4, 8, 16, 24, 32, etc.)
4. **Reusable components** - Extract common patterns into components
5. **Dark mode** - Add `dark:` variants when implementing dark mode
6. **Accessibility** - Include focus states, proper labels, and ARIA attributes

## VS Code Extension

Install the **Tailwind CSS IntelliSense** extension for:
- Autocomplete for Tailwind classes
- Linting and validation
- Hover previews of CSS values
- Color picker for color classes

## Resources

- [Tailwind CSS Documentation](https://tailwindcss.com/docs)
- [Tailwind UI Components](https://tailwindui.com/)
- [Tailwind Play (Online Editor)](https://play.tailwindcss.com/)
- [Headless UI (Unstyled components)](https://headlessui.com/)
