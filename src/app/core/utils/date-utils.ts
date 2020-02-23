
export function formatDate(date: Date): string {
    const mes = date.getMonth() + 1 < 10 ? `0${date.getMonth() + 1}` : date.getMonth() + 1;
    const dia = date.getDate() < 10 ? `0${date.getDate()}` : date.getDate();
    return `${date.getFullYear()}-${mes}-${dia}`;
}

export function formatDateParam(date: string | Date): string {
    if (typeof date === 'string') {
        return date.split('T')[0];
    } else {
        return date.toISOString().split('T')[0];
    }
}
