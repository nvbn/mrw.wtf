import { flatMap } from "lodash";

export const timeout = (delay) => new Promise(
  (resolve) => setTimeout(resolve, delay));

export const query = (node, match) => {
  let result = [];
  let notProcessed = [node];

  while (notProcessed.length) {
    result = [...result, ...notProcessed.filter(match)];
    notProcessed = flatMap(notProcessed, ({children}) => children || []);
  }

  return result;
};
