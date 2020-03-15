
export function toDateISOString(value: Date): string {
    const tzo = -value.getTimezoneOffset(),
        dif = tzo >= 0 ? '+' : '-',
        pad = (num) => {
            const norm = Math.floor(Math.abs(num));
            return (norm < 10 ? '0' : '') + norm;
        };
    return value.getFullYear() +
        '-' + pad(value.getMonth() + 1) +
        '-' + pad(value.getDate()) +
        'T' + pad(value.getHours()) +
        ':' + pad(value.getMinutes()) +
        ':' + pad(value.getSeconds()) +
        dif + pad(tzo / 60) +
        ':' + pad(tzo % 60);
}

export function formatDateTime(date: Date): string {
    return `${formatDate(date)} ${formatTime(date)}`;
}

export function formatDate(date: Date): string {
    const mes = date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1;
    const dia = date.getDate() < 10 ? `0${date.getDate()}` : date.getDate();
    return `${date.getFullYear()}-${mes}-${dia}`;
}

export function formatTime(date: Date): string {
    const hora = date.getHours() < 10 ? `0${date.getHours()}` : date.getHours();
    const minutos = date.getMinutes() < 10 ? `0${date.getMinutes()}` : date.getMinutes();
    const segundos = date.getSeconds() < 10 ? `0${date.getSeconds()}` : date.getSeconds();
    return `${hora}:${minutos}:${segundos}`;
}

export function parseToISODateTime(dateTime: any, includeTime = true): Date {
    if (dateTime) {
        if (includeTime) {
            dateTime = `${dateTime} ${formatTime(new Date())}`;
        }
        return new Date(`${dateTime}`);
    }
    return null;
}
