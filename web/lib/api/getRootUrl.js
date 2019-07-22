export default function getRootUrl() {
  const port = process.env.PORT || 8003;
  const dev = process.env.NODE_ENV !== 'production';
  const ROOT_URL = dev ?  `http://localhost:${port}`: `http://localhost:${port}`;
  console.log("The root url is : ",ROOT_URL);
  return ROOT_URL;
}