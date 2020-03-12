import { formatDate, formatDateTime, formatISODateTimeUTC } from './date-utils';

export function setInputDateTimeValue(date: Date): string {
    return formatISODateTimeUTC(formatDateTime(date), false);
}

export function getInputDateTimeValue(date: Date | string): string {
    if (typeof date === 'string') {
        return formatDateTime(new Date(date));

    } else if (typeof date === 'object') {
        return formatDateTime(date);
    }
}

export function getInputDateValue(date: Date | string): string {
    if (typeof date === 'string') {
        return formatDate(new Date(date));

    } else if (typeof date === 'object') {
        return formatDate(date);
    }
}
