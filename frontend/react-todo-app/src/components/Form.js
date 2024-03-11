import React from "react";

export default function Form({ handleSubmit, title, content, setValue, setContent }) {
  const handleChange = (e) => {
    setValue(e.target.title);
    setContent(e.target.content);
  };

  return (
    <form onSubmit={handleSubmit} className="flex pt-2">
      <input
        type="text"
        name="title"
        className="w-1/2 px-3 py-2 mr-4 text-gray-500 border rounded shadow"
        placeholder=""
        value={title}
        onChange={handleChange}
      />
      <input
        type="text"
        name="content"
        className="w-1/2 px-3 py-2 mr-4 text-gray-500 border rounded shadow"
        placeholder=""
        value={content}
        onChange={handleChange}
      />
      <input value="입력" type="submit"
        className="p-2 text-blue-400 border-2 border-blue-400 rounded hover:text-white hover:bg-blue-200" />
    </form>
  );
}
