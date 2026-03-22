/**
 * @typedef {Object} LoginRequest
 * @property {string} identifier
 */

/**
 * @typedef {Object} ResourceDto
 * @property {string} resourceID
 * @property {string} num
 * @property {string} name
 * @property {number} semester
 */

/**
 * @typedef {Object} TeacherMccDto
 * @property {number} teacherID
 * @property {boolean} isManager
 */

/**
 * @typedef {Object} McccResponse
 * @property {number} resourceID
 * @property {string} resourceCode
 * @property {number} minCM
 * @property {number} minTD
 * @property {number} minTP
 * @property {number} minDS
 * @property {number} minDSTP
 * @property {number} year
 * @property {number[]} saeIDs
 * @property {number[]} skillIDs
 * @property {TeacherMccDto[]} teachers
 * @property {string} creationDate - ISO date string
 * @property {string} editDate - ISO date string
 */
