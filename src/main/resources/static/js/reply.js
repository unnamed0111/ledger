async function getList({ano, page, size, goLast}) {
    const result = await axios.get(`/replies/list/${ano}`, {params: {page, size}})

    return result.data;
}

async function addReply(replyObj) {
    const response = await axios.post(`/replies/`, replyObj);

    return response.data
}

async function modifyReply(replyObj) {
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj);

    return response.data;
}