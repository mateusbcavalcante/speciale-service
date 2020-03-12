
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

export function formatISODateTimeUTC(dateTime: any, includeTime = true): string {
    if (dateTime) {
        if (includeTime) {
            dateTime = `${dateTime} ${formatTime(new Date())}`;
        }
        return new Date(`${dateTime} UTC`).toISOString();
    }
    return null;
}