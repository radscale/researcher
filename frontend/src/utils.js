export const parseSuffix = function (userData) {
    let suffix = ''
    if (userData.prof) {
        suffix += 'Prof.'
    } else if (userData.phd) {
        suffix += 'PhD'
    } else if (userData.msc) {
        suffix += 'MSc'
    } else if (userData.bsc) {
        suffix += 'BSc'
    }
    if (userData.eng) {
        suffix += ' eng.'
    }

    return suffix
}